package com.web.oneby.commons.Enums;

import java.util.Objects;

public enum Module {
    BOOK_MARKET(1, "book_market", "", "", "", false),
    ROOM_DISCUSSING(2, "room_discussing", "", "", "", true),
    MY_MARKET(3, "my_market", "", "", "", true),
    ;
    private int id;
    private String code;
    private String nameKZ;
    private String nameRU;
    private String nameEN;
    private boolean forPremium;

    Module(
            int id,
            String code,
            String nameKZ,
            String nameRU,
            String nameEN,
            boolean forPremium
    ){
        this.id = id;
        this.code = code;
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.forPremium = forPremium;
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

    public String getNameKZ() {
        return nameKZ;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getName(int language){
        if (language == Language.kz.getId()) {
            return nameKZ;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public boolean isForPremium() {
        return forPremium;
    }
}
