package com.web.oneby.commons.Enums;

import com.web.oneby.commons.Utils.LogUtil;

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

    private final static Map<String, Module> modulesByCode = new HashMap<>();
    private final static Map<Integer, Module> modulesById = new HashMap<>();

    static {
        for (Module module : values()) {
            modulesByCode.put(module.code, module);
            modulesById.put(module.id, module);
        }
    }

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
        try {
            return modulesByCode.get(code);
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
    }

    public static Module getModuleById(Integer id){
        try {
            return modulesById.get(id);
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
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
            put(Language.kk.suffix(), nameKK);
            put(Language.ru.suffix(), nameRU);
            put(Language.en.suffix(), nameEN);
        }};
        return names.get(Objects.requireNonNull(Language.getLanguageById(language)).suffix());
    }

    public String getName(Language language){
        Map<String, String> names = new HashMap<>() {{
            put(Language.kk.suffix(), nameKK);
            put(Language.ru.suffix(), nameRU);
            put(Language.en.suffix(), nameEN);
        }};
        return names.get(Objects.requireNonNull(language).suffix());
    }

    public boolean isForPremium() {
        return forPremium;
    }
}
