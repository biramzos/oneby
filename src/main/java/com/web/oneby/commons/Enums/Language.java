package com.web.oneby.commons.Enums;

import com.web.oneby.commons.Utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    kk("Қазақша", "Казахский", "Kazakh"),
    ru("Орысша", "Русский", "Russian"),
    en("Ағылшынша", "Английский", "English");

    private String nameKK;
    private String nameRU;
    private String nameEN;
    private final static Map<String, Language> languagesByCode = new HashMap<>();

    static {
        for (Language language: values()) {
            languagesByCode.put(language.name(), language);
        }
    }

    Language (
        String nameKK,
        String nameRU,
        String nameEN
    ) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public String getName(Language language) {
        Map<String, String> names = new HashMap<>() {{
            put(kk.suffix(), nameKK);
            put(ru.suffix(), nameRU);
            put(en.suffix(), nameEN);
        }};
        return names.get(language.suffix());
    }

    public static boolean contains(String language) {
        return languagesByCode.containsKey(language);
    }

    public String suffix() {
        return this.name().toUpperCase();
    }
}
