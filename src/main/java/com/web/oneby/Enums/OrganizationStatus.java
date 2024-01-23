package com.web.oneby.Enums;

import com.web.oneby.Models.Organization;

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
}
