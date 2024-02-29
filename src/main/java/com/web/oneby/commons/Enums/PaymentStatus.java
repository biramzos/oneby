package com.web.oneby.commons.Enums;


import java.util.HashMap;
import java.util.Map;

public enum PaymentStatus {
    SUCCESS("", "", "Successfully payed!"),
    ERROR("", "", "Error while paying!");

    PaymentStatus(String nameKK, String nameRU, String nameEN){
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKK;
    private String nameRU;
    private String nameEN;

    public String getName(int language){
        Map<String, String> names = new HashMap<>() {{
            put("nameKK", nameKK);
            put("nameRU", nameRU);
            put("nameEN", nameEN);
        }};
        return names.get("name" + Language.getLanguageById(language).suffix());
    }

    public String getName(Language language){
        Map<String, String> names = new HashMap<>() {{
            put("nameKK", nameKK);
            put("nameRU", nameRU);
            put("nameEN", nameEN);
        }};
        return names.get("name" + language.suffix());
    }
}
