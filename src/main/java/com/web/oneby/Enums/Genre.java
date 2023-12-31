package com.web.oneby.Enums;

public enum Genre {

    FANTASY("Фэнтези", "Фэнтези", "Fantasy"),
    SCIENCE_FICTION("Ғылыми фантастика", "Научная фантастика", "Science fiction"),
    REALISM("Реализм", "Реализм", "Realism"),
    ROMANCE("Роман", "Роман", "Romance"),
    DETECTIVE("Детектив", "Детектив", "Detective"),
    THRILLER("Триллер", "Триллер", "Thriller"),
    HORROR("Киімділік", "Ужасы", "Horror"),
    ADVENTURE("Мактаптау", "Приключения", "Adventure"),
    HISTORICAL_FICTION("Тарихи роман", "Исторический роман", "Historical Fiction"),
    PHILOSOPHY("Философия", "Философия", "Philosophy"),
    PSYCHOLOGY("Психологиялық", "Психологические", "Psychological"),
    BIOGRAPHY("Биография", "Биография", "Biography"),
    HUMOR("Күлкілік", "Юмор", "Humor"),
    DRAMA("Драма", "Драма", "Drama"),
    POETRY("Поэзия", "Поэзия", "Poetry"),
    EDUCATIONAL("Білім біру", "Учебные", "Educational"),
    OTHER("Баскалары", "Другие", "Others"),

    ;
    Genre(String nameKZ, String nameRU, String nameEN){
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKZ;
    private String nameRU;
    private String nameEN;

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

}
