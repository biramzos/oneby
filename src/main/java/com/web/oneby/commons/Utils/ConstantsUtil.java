package com.web.oneby.commons.Utils;

import com.web.oneby.modules.users.Models.User;
import jakarta.servlet.ServletContext;
import lombok.SneakyThrows;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import java.net.InetAddress;

public class ConstantsUtil {
    //ADMIN info
    public static final String ADMIN_NAME_KZ = "Әкімші";
    public static final String ADMIN_NAME_RU = "Администратор";
    public static final String ADMIN_NAME_EN = "Administrator";
    public static final String ADMIN_LASTNAME_KZ = "Әкімші";
    public static final String ADMIN_LASTNAME_RU = "Администратор";
    public static final String ADMIN_LASTNAME_EN = "Administrator";
    public static final String ADMIN_USERNAME = "admin_one_by";
    public static final String ADMIN_PASSWORD = "12345678";
    public static final String ADMIN_EMAIL = "admin@oneby.kz";

    public static final String TEMPLATES_DIRECTORY = new ClassPathResource("/assets/templates/").getPath();
    public static final String FONTS_DIRECTORY = new ClassPathResource("/assets/fonts/").getPath();
    public static final String IMAGES_DIRECTORY = new ClassPathResource("/assets/images/").getPath();
    public static final String VIDEOS_DIRECTORY = new ClassPathResource("/assets/videos/").getPath();
    public static final String FILES_DIRECTORY = new ClassPathResource("/assets/files/").getPath();

    @SneakyThrows
    public static String getHostName() {
        return InetAddress.getLocalHost().getHostName();
    }
}
