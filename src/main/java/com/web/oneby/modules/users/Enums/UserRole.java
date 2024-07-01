package com.web.oneby.modules.users.Enums;

import com.web.oneby.commons.Enums.Language;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public String getName(Language language) {
        Map<String, String> names = new HashMap<>() {{
            put(Language.kk.suffix(), nameKK);
            put(Language.ru.suffix(), nameRU);
            put(Language.en.suffix(), nameEN);
        }};
        return names.get(language.suffix());
    }
}
