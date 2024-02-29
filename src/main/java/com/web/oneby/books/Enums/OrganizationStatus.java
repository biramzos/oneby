package com.web.oneby.books.Enums;


import com.web.oneby.commons.Enums.Language;

import java.util.HashMap;
import java.util.Map;

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

    public String getName(int language) {
        Map<String, String> names = new HashMap<>() {{
            put("nameKK", nameKK);
            put("nameRU", nameRU);
            put("nameEN", nameEN);
        }};
        return names.get("name" + Language.getLanguageById(language).suffix());
    }

    public String getName(Language language) {
        Map<String, String> names = new HashMap<>() {{
            put("nameKK", nameKK);
            put("nameRU", nameRU);
            put("nameEN", nameEN);
        }};
        return names.get("name" + language.suffix());
    }
}
