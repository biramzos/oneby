package com.web.oneby.books.Enums;


import com.web.oneby.commons.Enums.Language;

public enum BasketStatus {
    CREATED("", "", "Created!"),
    PAYED("", "", "Payed!"),
    REMOVED("", "", "Removed!");


    BasketStatus(String nameKZ, String nameRU, String nameEN){
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKZ;
    private String nameRU;
    private String nameEN;

    public String getName(int language){
        if (language == Language.kz.getId()) {
            return nameKZ;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }
}
