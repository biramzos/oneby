package com.web.oneby.commons.Utils;

import org.springframework.beans.factory.annotation.Value;

public class ConstantsUtil {
    @Value("${env.url}")
    public static String HOST;

    //ADMIN info
    public static String ADMIN_NAME_KZ = "Әкімші";
    public static String ADMIN_NAME_RU = "Администратор";
    public static String ADMIN_NAME_EN = "Administrator";
    public static String ADMIN_LASTNAME_KZ = "Әкімші";
    public static String ADMIN_LASTNAME_RU = "Администратор";
    public static String ADMIN_LASTNAME_EN = "Administrator";
    public static String ADMIN_USERNAME = "admin_one_by";
    public static String ADMIN_PASSWORD = "12345678";
    public static String ADMIN_EMAIL = "admin@oneby.kz";
}
