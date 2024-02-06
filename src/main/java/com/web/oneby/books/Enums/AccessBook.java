package com.web.oneby.books.Enums;


import com.web.oneby.commons.Enums.Language;

public enum AccessBook {
    PUBLIC("Жалпыға қолжетімділік", "Публичный доступ", "Public access"),
    PRIVATE("Жеке қатынас", "Приватный доступ", "Private access")
    ;

    AccessBook(String nameKZ, String nameRU, String nameEN){
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKZ;
    private String nameRU;
    private String nameEN;

    public String getNameKZ() {
        return nameKZ;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getName(int language) {
        if (language == Language.kz.getId()) {
            return nameKZ;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public String getName(Language language) {
        if (language == Language.kz) {
            return nameKZ;
        } else if (language == Language.ru) {
            return nameRU;
        } else {
            return nameEN;
        }
    }
}
