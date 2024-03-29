package com.web.oneby.commons.Enums;

import java.util.HashMap;
import java.util.Map;

public enum PaymentStatus {
    SUCCESS("Сәтті", "Успешно", "Successfully"),
    ERROR("Төленбеген", "Не оплачено", "Not paid");

    PaymentStatus(String nameKK, String nameRU, String nameEN){
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKK;
    private String nameRU;
    private String nameEN;

    public String getName(int language){
        Map<String, String> names = new HashMap<>() {{
            put(Language.kk.suffix(), nameKK);
            put(Language.ru.suffix(), nameRU);
            put(Language.en.suffix(), nameEN);
        }};
        return names.get(Language.getLanguageById(language).suffix());
    }

    public String getName(Language language){
        Map<String, String> names = new HashMap<>() {{
            put(Language.kk.suffix(), nameKK);
            put(Language.ru.suffix(), nameRU);
            put(Language.en.suffix(), nameEN);
        }};
        return names.get(language.suffix());
    }
}
