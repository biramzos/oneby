package com.web.oneby.books.Enums;


import com.web.oneby.commons.Enums.Language;

public enum OrganizationStatus {
    APPLIED("", "", "Applied"),
    VERIFIED("", "", "Verified");

    OrganizationStatus (
            String nameKZ,
            String nameRU,
            String nameEN
    ) {
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKZ;
    private String nameRU;
    private String nameEN;

    private String getName(int language) {
        if (language == Language.kz.getId()) {
            return nameKZ;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    private String getName(Language language) {
        if (language == Language.kz) {
            return nameKZ;
        } else if (language == Language.ru) {
            return nameRU;
        } else {
            return nameEN;
        }
    }
}
