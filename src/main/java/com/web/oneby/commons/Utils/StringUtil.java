package com.web.oneby.commons.Utils;

public class StringUtil {

    public static boolean isEmpty(String s) {
        return (s == null) || (s.isEmpty());
    }

    public static boolean isNotEmpty(String s) {
        return !(isEmpty(s));
    }

}
