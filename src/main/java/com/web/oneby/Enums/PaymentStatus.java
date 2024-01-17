package com.web.oneby.Enums;

public enum PaymentStatus {
    SUCCESS("", "", "Successfully payed!"),
    ERROR("", "", "Error while paying!");

    PaymentStatus(String nameKZ, String nameRU, String nameEN){
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
