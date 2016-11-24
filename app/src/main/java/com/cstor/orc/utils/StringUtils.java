package com.cstor.orc.utils;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class StringUtils {


    public static boolean isNull(String value) {
        if ((null != value) && (!("").equals(value))) {
            return false;
        } else {
            return true;
        }
    }
}
