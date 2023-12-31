package com.web.oneby.Enums;

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
    private String nameKZ;

    Month (int id, String nameKZ, String nameRU, String nameEN) {
        this.id = id;
        this.nameKZ = nameKZ;
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

    public String getNameKZ() {
        return nameKZ;
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
}
