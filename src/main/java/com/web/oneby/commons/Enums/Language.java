package com.web.oneby.commons.Enums;

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

    public String getNameEN() {
        return nameEN;
    }

    public String getNameKK() {
        return nameKK;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getName(int language) {
        Map<String, String> names = new HashMap<>() {{
            put("nameKK", nameKK);
            put("nameRU", nameRU);
            put("nameEN", nameEN);
        }};
        return names.get("name" + getLanguageById(language).suffix());
    }

    public String getName(Language language) {
        Map<String, String> names = new HashMap<>() {{
            put("nameKK", nameKK);
            put("nameRU", nameRU);
            put("nameEN", nameEN);
        }};
        return names.get("name" + language.suffix());
    }

    public static Language getLanguageById(int languageID) {
        Language language = null;
        for (Language l: values()) {
            if (languageID == l.getId()) {
                language = l;
                break;
            }
        }
        return language;
    }

    public static boolean contains(String language) {
        boolean isExist = false;
        for (Language l: values()) {
            if (l.name().equals(language.toLowerCase())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public String suffix() {
        return this.name().toUpperCase();
    }
}
