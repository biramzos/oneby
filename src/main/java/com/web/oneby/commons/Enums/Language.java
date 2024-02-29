package com.web.oneby.commons.Enums;

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
        if (language == Language.kk.getId()) {
            return nameKK;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public String getName(Language language) {
        if (language == Language.kk) {
            return nameKK;
        } else if (language == Language.ru) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public static Language getLanguageById(int language) {
        if (language == Language.kk.getId()) {
            return Language.kk;
        } else if (language == Language.ru.getId()) {
            return Language.ru;
        } else {
            return Language.en;
        }
    }

    public static boolean contains(String language) {
        if (language.equals(Language.kk.name())) {
            return true;
        } else if (language.equals(Language.ru.name())) {
            return true;
        } else if (language.equals(Language.en.name())) {
            return true;
        } else {
            return false;
        }
    }

    public String suffix() {
        return this.name().toUpperCase();
    }
}
