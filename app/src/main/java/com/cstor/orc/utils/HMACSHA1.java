package com.cstor.orc.utils;

import android.util.Base64;

import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA1 {

	private static final String HMAC_SHA1 = "HmacSHA1";

	public static byte[] getSignature(String data, String key) throws Exception {
		Mac mac = Mac.getInstance(HMAC_SHA1);
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
				mac.getAlgorithm());
		mac.init(signingKey);

		return mac.doFinal(data.getBytes());
	}


	/**
	 *  签名方法
	 *  本方法将Request中的httpMethod、headers、path、queryParam、formParam合成一个字符串用hmacSha256算法双向加密进行签名
	 */
	public static String sign(String method , Map<String, String> headersParams , String pathWithParameter , Map<String, String> queryParams , Map<String, String> formParam) {
		try {
			Mac hmacSha256 = Mac.getInstance("HmacSHA256");
			byte[] keyBytes = Constant.AliASecret.getBytes(Constant.CLOUDAPI_ENCODING);
			hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

			//将Request中的httpMethod、headers、path、queryParam、formParam合成一个字符串
			String signString = buildStringToSign(method , headersParams , pathWithParameter , queryParams , formParam);

			//对字符串进行hmacSha256加密，然后再进行BASE64编码
			byte[] signResult = hmacSha256.doFinal(signString.getBytes(Constant.CLOUDAPI_ENCODING));
			byte[] base64Bytes = Base64.encode(signResult , Base64.DEFAULT);
			return new String(base64Bytes , Constant.CLOUDAPI_ENCODING);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将Request中的httpMethod、headers、path、queryParam、formParam合成一个字符串
	 */
	private static String buildStringToSign(String method , Map<String, String> headerParams, String pathWithParameter, Map<String, String> queryParams ,  Map<String, String> formParams) {

		StringBuilder sb = new StringBuilder();
		sb.append(method).append(Constant.CLOUDAPI_LF);

		//如果有@"Accept"头，这个头需要参与签名
		if (headerParams.get("Accept") != null) {
			sb.append(headerParams.get("Accept"));
		}
		sb.append(Constant.CLOUDAPI_LF);

		//如果有@"Content-MD5"头，这个头需要参与签名
		if (headerParams.get("Content-MD5") != null) {
			sb.append(headerParams.get("Content-MD5"));
		}
		sb.append(Constant.CLOUDAPI_LF);

		//如果有@"Content-Type"头，这个头需要参与签名
		if (headerParams.get("Content-Type") != null) {
			sb.append(headerParams.get("Content-Type"));
		}
		sb.append(Constant.CLOUDAPI_LF);

		//签名优先读取HTTP_CA_HEADER_DATE，因为通过浏览器过来的请求不允许自定义Date（会被浏览器认为是篡改攻击）
		if (headerParams.get("Date") != null) {
			sb.append(headerParams.get("Date"));
		}
		sb.append(Constant.CLOUDAPI_LF);

		//将headers合成一个字符串
		sb.append(buildHeaders(headerParams));

		//将path、queryParam、formParam合成一个字符串
		sb.append(buildResource(pathWithParameter, queryParams , formParams));
		return sb.toString();
	}

	/**
	 * 将path、queryParam、formParam合成一个字符串
	 */
	private static String buildResource(String pathWithParameter, Map<String, String> queryParams ,  Map<String, String> formParams) {
		StringBuilder result = new StringBuilder();
		result.append(pathWithParameter);

		//使用TreeMap,默认按照字母排序
		TreeMap<String , String> parameter = new TreeMap<>();
		if(null!= queryParams && queryParams.size() > 0){
			parameter.putAll(queryParams);
		}

		if(null != formParams && formParams.size() > 0){
			parameter.putAll(formParams);
		}

		if(parameter.size() > 0) {
			result.append("?");
			boolean isFirst = true;
			for (String key : parameter.keySet()) {
				if (isFirst == false) {
					result.append("&");
				} else {
					isFirst = false;
				}
				result.append(key).append("=").append(parameter.get(key));
			}
		}
		return result.toString();
	}


	/**
	 *  将headers合成一个字符串
	 *  需要注意的是，HTTP头需要按照字母排序加入签名字符串
	 *  同时所有加入签名的头的列表，需要用逗号分隔形成一个字符串，加入一个新HTTP头@"X-Ca-Signature-Headers"
	 */
	private static String buildHeaders(Map<String, String> headers) {
		//使用TreeMap,默认按照字母排序
		Map<String, String> headersToSign = new TreeMap<String, String>();

		if (headers != null) {
			StringBuilder signHeadersStringBuilder = new StringBuilder();

			int flag = 0;
			for (Map.Entry<String, String> header : headers.entrySet()) {
				if (header.getKey().startsWith(Constant.CLOUDAPI_CA_HEADER_TO_SIGN_PREFIX_SYSTEM)) {
					if (flag != 0) {
						signHeadersStringBuilder.append(",");
					}
					flag++;
					signHeadersStringBuilder.append(header.getKey());
					headersToSign.put(header.getKey(), header.getValue());
				}
			}

			//同时所有加入签名的头的列表，需要用逗号分隔形成一个字符串，加入一个新HTTP头@"X-Ca-Signature-Headers"
			headers.put("X-Ca-Signature-Headers", signHeadersStringBuilder.toString());
		}

		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e : headersToSign.entrySet()) {
			sb.append(e.getKey()).append(':').append(e.getValue()).append(Constant.CLOUDAPI_LF);
		}
		return sb.toString();
	}

}
