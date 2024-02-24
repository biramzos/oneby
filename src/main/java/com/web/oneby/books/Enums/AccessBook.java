package com.web.oneby.books.Enums;


import com.web.oneby.commons.Enums.Language;

public enum AccessBook {
    PUBLIC("Жалпыға қолжетімділік", "Публичный доступ", "Public access"),
    PRIVATE("Жеке қатынас", "Приватный доступ", "Private access")
    ;

    AccessBook(String nameKK, String nameRU, String nameEN){
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKK;
    private String nameRU;
    private String nameEN;

    public String getNameKK() {
        return nameKK;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
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
}
