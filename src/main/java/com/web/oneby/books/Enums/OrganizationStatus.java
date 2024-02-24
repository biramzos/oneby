package com.web.oneby.books.Enums;


import com.web.oneby.commons.Enums.Language;

public enum OrganizationStatus {
    APPLIED("", "", "Applied"),
    VERIFIED("", "", "Verified");

    OrganizationStatus (
            String nameKK,
            String nameRU,
            String nameEN
    ) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKK;
    private String nameRU;
    private String nameEN;

    private String getName(int language) {
        if (language == Language.kk.getId()) {
            return nameKK;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    private String getName(Language language) {
        if (language == Language.kk) {
            return nameKK;
        } else if (language == Language.ru) {
            return nameRU;
        } else {
            return nameEN;
        }
    }
}
