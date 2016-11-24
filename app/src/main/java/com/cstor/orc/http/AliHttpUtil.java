package com.cstor.orc.http;

import android.util.Log;

import com.cstor.orc.utils.Base64Encoder;
import com.cstor.orc.utils.Constant;
import com.cstor.orc.utils.HMACSHA1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by Administrator on 2016/10/19.
 */

public class AliHttpUtil {

    private  static  String TAG="zkk--";

    public static void uploadIdCard(String bitmap, String card_type, final SimpleCallBack callback) {
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            JSONObject configObj = new JSONObject();
            JSONObject obj = new JSONObject();
            JSONArray inputArray = new JSONArray();
            configObj.put("side", "face");
            obj.put("image", getParam(50, bitmap));
            obj.put("configure", getParam(50, configObj.toString()));
            inputArray.put(obj);
            requestObj.put("inputs", inputArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String body = requestObj.toString();

        /**
         * 将pathParams中的value替换掉path中的动态参数
         * 比如 path=/v2/getUserInfo/[userId]，pathParams 字典中包含 key:userId , value:10000003
         * 替换后path会变成/v2/getUserInfo/10000003
         */


        /*
             * http header 参数
             */
        String method = "POST";
        String accept = "json";
        String content_type = "application/octet-stream";
        String date = toGMTString(new Date());

        RequestParams params = new RequestParams("https://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json");
        Date current = new Date();

        params.addHeader("Date", date);
        params.addHeader("Content-Type", content_type);
        params.addHeader("Host", "dm-51.data.aliyun.com");
        params.addHeader("X-Ca-Key", Constant.AliAkey);
        params.addHeader("Accept", "application/json");
        params.addHeader("X-Ca-Timestamp", String.valueOf(current.getTime()));
        params.addHeader("User-Agent", "Apache-HttpClient/4.1.2 (java 1.6)");
        params.addHeader("CA_VERSION", "1");
        params.addHeader("X-Ca-Signature", "1");

        Map<String , String> formParams=new HashMap<String , String>();
        formParams.put("Date", date);
        formParams.put("Content-Type", content_type);
        formParams.put("Host", "dm-51.data.aliyun.com");
        formParams.put("Accept", "application/json");
        formParams.put("User-Agent", "Apache-HttpClient/4.1.2 (java 1.6)");
        formParams.put("X-Ca-Key", Constant.AliAkey);
        formParams.put("X-Ca-Timestamp", String.valueOf(current.getTime()));
        formParams.put("CA_VERSION", "1");
        params.addHeader("X-Ca-Signature", HMACSHA1.sign(method , formParams , null , null , null));



        params.addBodyParameter("", body);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG,"ppppppppppppppppp-----------"+result);
                callback.Succ(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG,"onError----"+ex.getMessage());
                callback.error();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG,"ppppppppppppppppp"+"onCancelled() ");
            }

            @Override
            public void onFinished() {
                Log.d(TAG,"ppppppppppppppppp"+"onFinished() ");

            }
        });

    }

    public interface SimpleCallBack {
        void Succ(String result);

        void error();
    }



    /*
     * 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }


    /*
         * 计算MD5+BASE64
         */
    public static String MD5Base64(String s) {
        if (s == null)
            return null;
        String encodeStr = "";
        byte[] utfBytes = s.getBytes();
        MessageDigest mdTemp;
        try {
            mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(utfBytes);
            byte[] md5Bytes = mdTemp.digest();
            Base64Encoder b64Encoder = new Base64Encoder();
            encodeStr = b64Encoder.encode(md5Bytes);
        } catch (Exception e) {
            throw new Error("Failed to generate MD5 : " + e.getMessage());
        }
        return encodeStr;
    }
    /*
     * 计算 HMAC-SHA1
     */
    public static String HMACSha1(String data, String key) {
        String result;
        try {
            // System.out.println("data: " + data);
            // System.out.println("key: " + key);
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = (new Base64Encoder()).encode(rawHmac);
        } catch (Exception e) {
            throw new Error("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
    /*
     * 等同于javaScript中的 new Date().toUTCString();
     */
    public static String toGMTString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

}
