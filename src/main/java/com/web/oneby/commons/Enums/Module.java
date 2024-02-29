package com.web.oneby.commons.Enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Module {
    BOOK_MARKET(1, "book_market", "", "", "", false),
    ROOM_DISCUSSING(2, "room_discussing", "", "", "", true),
    SPORT_MARKET(3, "sport_market", "", "", "", true),
    ACADEMY(4, "academy", "", "", "", false, true),
    ;
    private final int id;
    private final String code;
    private final String nameKK;
    private final String nameRU;
    private final String nameEN;
    private final boolean forPremium;
    private final boolean isStudy;

    Module(
            int id,
            String code,
            String nameKK,
            String nameRU,
            String nameEN,
            boolean forPremium
    ){
        this.id = id;
        this.code = code;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.forPremium = forPremium;
        this.isStudy = false;
    }

    Module(
            int id,
            String code,
            String nameKK,
            String nameRU,
            String nameEN,
            boolean forPremium,
            boolean isStudy
    ){
        this.id = id;
        this.code = code;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.forPremium = forPremium;
        this.isStudy = isStudy;
    }

    public static Module getModuleByCode(String code){
        Module module = null;
        for (Module m: values()) {
            if (Objects.equals(m.code, code)) {
                module = m;
                break;
            }
        }
        if (module == null) {
            throw new RuntimeException("Module '" + code + "' not found!");
        }
        return module;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNameKK() {
        return nameKK;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getName(int language){
        Map<String, String> names = new HashMap<>() {{
            put("nameKK", nameKK);
            put("nameRU", nameRU);
            put("nameEN", nameEN);
        }};
        return names.get("name" + Language.getLanguageById(language).suffix());
    }

    public boolean isForPremium() {
        return forPremium;
    }
}
