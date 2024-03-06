package com.web.oneby.modules.users.Enums;

import com.web.oneby.commons.Enums.Language;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

public enum UserRole implements GrantedAuthority {
    ADMIN("Әкімші", "Администратор", "Administrator"),
    SELLER("Сатушы", "Продавец", "Seller"),
    TUTOR("Мұғалім", "Учитель", "Tutor"),
    PREMIUM("Премиум пайдаланушы", "Премиум пользователь", "Premium user"),
    USER("Пайдаланушы", "Пользователь", "User");

    @Override
    public String getAuthority() {
        return name();
    }

    private String nameKK;
    private String nameRU;
    private String nameEN;

    UserRole(
            String nameKK,
            String nameRU,
            String nameEN
    ){
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
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
