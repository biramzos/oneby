package com.web.oneby.commons.Enums;



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
        if (language == Language.kk.getId()) {
            return nameKK;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public String getName(Language language){
        if (language == Language.kk) {
            return nameKK;
        } else if (language == Language.ru) {
            return nameRU;
        } else {
            return nameEN;
        }
    }
}
