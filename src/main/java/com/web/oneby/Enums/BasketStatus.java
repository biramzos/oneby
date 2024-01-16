package com.web.oneby.Enums;

public enum BasketStatus {
    CREATED("", "", "Created"),
    PAYED("", "", "Payed"),
    REMOVED("", "", "Removed");


    BasketStatus(String nameKZ, String nameRU, String nameEN){
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKZ;
    private String nameRU;
    private String nameEN;
}
