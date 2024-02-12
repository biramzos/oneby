package com.web.oneby.commons.Utils;

import org.springframework.beans.factory.annotation.Value;

public class ConstantsUtil {
    @Value("${env.url}")
    public static String HOST;
    @Value("${env.admin_name_kz}")
    public static String ADMIN_NAME_KZ = "Әкімші";
    @Value("${env.admin_name_ru}")
    public static String ADMIN_NAME_RU = "Администратор";
    @Value("${env.admin_name_en}")
    public static String ADMIN_NAME_EN = "Administrator";
    @Value("${env.admin_lastname_kz}")
    public static String ADMIN_LASTNAME_KZ = "Әкімші";
    @Value("${env.admin_lastname_ru}")
    public static String ADMIN_LASTNAME_RU = "Администратор";
    @Value("${env.admin_lastname_en}")
    public static String ADMIN_LASTNAME_EN = "Administrator";
    @Value("${env.admin_username}")
    public static String ADMIN_USERNAME = "admin_one_by";
    @Value("${env.admin_password}")
    public static String ADMIN_PASSWORD = "12345678";
    @Value("${env.admin_email}")
    public static String ADMIN_EMAIL = "admin@oneby.kz";
}
