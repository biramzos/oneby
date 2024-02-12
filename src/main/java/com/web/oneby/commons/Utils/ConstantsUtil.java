package com.web.oneby.commons.Utils;

import org.springframework.beans.factory.annotation.Value;

public class ConstantsUtil {
    @Value("${env.url}")
    public static String HOST;
    @Value("${env.admin.username}")
    public static String ADMIN_NAME_KZ;
    @Value("${env.admin.username}")
    public static String ADMIN_NAME_RU;
    @Value("${env.admin.username}")
    public static String ADMIN_NAME_EN;
    @Value("${env.admin.username}")
    public static String ADMIN_LASTNAME_KZ;
    @Value("${env.admin.username}")
    public static String ADMIN_LASTNAME_RU;
    @Value("${env.admin.username}")
    public static String ADMIN_LASTNAME_EN;
    @Value("${env.admin.username}")
    public static String ADMIN_USERNAME;
    @Value("${env.admin.password}")
    public static String ADMIN_PASSWORD;
    @Value("${env.admin.email}")
    public static String ADMIN_EMAIL;
}
