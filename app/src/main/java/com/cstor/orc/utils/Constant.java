package com.cstor.orc.utils;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/10/19.
 */

public class  Constant {

    public static String AppID = "10009061";
    public static String SecretID = "AKIDmggu6Nr7X6b7jfBPKFB7ueGl5EgSwtNw";
    public static String SecretKey = "lLjwxrZAqONEtRq34sU4VpKl1C0hCVin";
    public  static  int EXPIRED_SECONDS = 2592000;


    public  static String AliAkey="23541626";
    public static String AliASecret="ac91d0b820019bdf6e8365b50255e1f8 ";

    //编码UTF-8
    public static final Charset CLOUDAPI_ENCODING = Charset.forName("UTF-8");

    //换行符
    public static final String CLOUDAPI_LF = "\n";

    //参与签名的系统Header前缀,只有指定前缀的Header才会参与到签名中
    public static final String CLOUDAPI_CA_HEADER_TO_SIGN_PREFIX_SYSTEM = "X-Ca-";
}
