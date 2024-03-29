package com.web.oneby.commons.Enums;

import com.web.oneby.commons.Utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    kk(1, "Қазақша", "Казахский", "Kazakh"),
    ru(2, "Орысша", "Русский", "Russian"),
    en(3, "Ағылшынша", "Английский", "English");

    private int id;
    private String nameKK;
    private String nameRU;
    private String nameEN;

    private final static Map<Integer, Language> languagesById = new HashMap<>();
    private final static Map<String, Language> languagesByName = new HashMap<>();

    static {
        for (Language language: values()) {
            languagesById.put(language.id, language);
            languagesByName.put(language.name(), language);
        }
    }

    Language (
        int id,
        String nameKK,
        String nameRU,
        String nameEN
    ) {
        this.id = id;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }


    public int getId() {
        return id;
    }

    public String getName(int language) {
        Map<String, String> names = new HashMap<>() {{
            put(kk.suffix(), nameKK);
            put(ru.suffix(), nameRU);
            put(en.suffix(), nameEN);
        }};
        return names.get(getLanguageById(language).suffix());
    }

    public String getName(Language language) {
        Map<String, String> names = new HashMap<>() {{
            put(kk.suffix(), nameKK);
            put(ru.suffix(), nameRU);
            put(en.suffix(), nameEN);
        }};
        return names.get(language.suffix());
    }

    public static Language getLanguageById(int languageID) {
        try {
            return languagesById.get(languageID);
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
    }

    public static boolean contains(String language) {
        return languagesByName.containsKey(language);
    }

    public String suffix() {
        return this.name().toUpperCase();
    }
}
