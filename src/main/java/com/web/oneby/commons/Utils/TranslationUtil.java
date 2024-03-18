package com.web.oneby.commons.Utils;

import com.web.oneby.commons.Enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Properties;

@Component
public class TranslationUtil {

    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        TranslationUtil.messageSource = messageSource;
    }

    public static String getMessage(String key, Language language, Object... arguments) {
        return messageSource.getMessage(key, arguments, new Locale(language.name()));
    }

    public static String getMessage(String key, int language, String... args) {
        return messageSource.getMessage(key, args, new Locale(Language.getLanguageById(language).name()));
    }

    public static String getMessage(String key, Language language) {
        return messageSource.getMessage(key, null, new Locale(language.name()));
    }

    public static String getMessage(String key, int language) {
        return messageSource.getMessage(key, null, new Locale(Language.getLanguageById(language).name()));
    }
}
