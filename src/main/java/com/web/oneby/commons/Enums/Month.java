package com.web.oneby.commons.Enums;

import java.util.HashMap;
import java.util.Map;

public enum Month {
    JANUARY(1, "Қаңтар", "Январь", "January"),
    FEBRUARY(2, "Ақпан", "Февраль", "February"),
    MARCH(3, "Наурыз", "Март", "March"),
    APRIL(4, "Сәуір", "Апрель", "April"),
    MAY(5, "Мамыр", "Май", "May"),
    JUNE(6, "Маусым", "Июнь", "June"),
    JULY(7, "Шілде", "Июль", "July"),
    AUGUST(8, "Тамыз", "Август", "August"),
    SEPTEMBER(9, "Қыркүйек", "Сентябрь", "September"),
    OCTOBER(10, "Қазан", "Октябрь", "October"),
    NOVEMBER(11, "Қараша", "Ноябрь", "November"),
    DECEMBER(12, "Желтоқсан", "Декабрь", "December");


    private int id;
    private String nameEN;
    private String nameRU;
    private String nameKK;

    Month (int id, String nameKK, String nameRU, String nameEN) {
        this.id = id;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public static Month fromId (int id) {
        Month month = null;
        for (Month m: Month.values()) {
            if (m.getId() == id) {
                month = m;
                break;
            }
        }
        return month;
    }

    public int getId() {
        return id;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameKK() {
        return nameKK;
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
