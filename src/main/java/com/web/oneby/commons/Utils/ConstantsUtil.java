package com.web.oneby.commons.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.oneby.commons.Enums.LogType;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
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
    public static final String LOGOS_DIRECTORY = new ClassPathResource("/assets/logos/").getPath();
    public static final String VIDEOS_DIRECTORY = new ClassPathResource("/assets/videos/").getPath();
    public static final String FILES_DIRECTORY = new ClassPathResource("/assets/files/").getPath();

    @Autowired
    private ApplicationContext applicationContext;

    public static String getServerAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getServerAddress(request);
    }

    public static String getServerAddress(HttpServletRequest request) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
        return builder.build().getHost();
    }

    public static int getServerPort() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getServerPort(request);
    }

    public static int getServerPort(HttpServletRequest request) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
        return builder.build().getPort();
    }

    public static String getDomain() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
        return builder.build().getHost();
    }

    public static String getHostName() {
        return getDomain() + ((getServerPort() != 80) ?  ":" + getServerPort() : "");
    }
}
