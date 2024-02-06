package com.web.oneby.commons.Enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN(1, "Әкімші", "Администратор", "Administrator"),
    SELLER(2, "Сатушы", "Продавец", "Seller"),
    PREMIUM(2, "Премиум пайдаланушы", "Премиум пользователь", "Premium user"),
    USER(3, "Пайдаланушы", "Пользователь", "User");

    @Override
    public String getAuthority() {
        return name();
    }

    private int id;
    private String nameKZ;
    private String nameRU;
    private String nameEN;

    UserRole(
            int id,
            String nameKZ,
            String nameRU,
            String nameEN
    ){
        this.id = id;
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public static UserRole getById(int id) {
        UserRole r = null;
        for (UserRole role:values()) {
            if (role.getId() == id) {
                r = role;
                break;
            }
        }
        return r;
    }

    public int getId() {
        return id;
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

    public String getName(int language) {
        if (language == Language.kz.getId()) {
            return nameKZ;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public String getName(Language language) {
        if (language == Language.kz) {
            return nameKZ;
        } else if (language == Language.ru) {
            return nameRU;
        } else {
            return nameEN;
        }
    }
}
