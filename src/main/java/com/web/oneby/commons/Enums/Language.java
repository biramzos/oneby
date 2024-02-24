package com.web.oneby.commons.Enums;

public enum Language {
    kk(1, "Қазақша", "Казахский", "Kazakh"),
    ru(2, "Орысша", "Русский", "Russian"),
    en(3, "Ағылшынша", "Английский", "English");

    private int id;
    private String nameKZ;
    private String nameRU;
    private String nameEN;

    Language (
        int id,
        String nameKZ,
        String nameRU,
        String nameEN
    ) {
        this.id = id;
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }


    public int getId() {
        return id;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getNameKZ() {
        return nameKZ;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getName(int language) {
        if (language == Language.kk.getId()) {
            return nameKZ;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public String getName(Language language) {
        if (language == Language.kk) {
            return nameKZ;
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
}
