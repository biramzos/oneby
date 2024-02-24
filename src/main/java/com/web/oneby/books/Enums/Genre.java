package com.web.oneby.books.Enums;


import com.web.oneby.commons.Enums.Language;

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
    Genre(String nameKK, String nameRU, String nameEN){
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private String nameKK;
    private String nameRU;
    private String nameEN;

    public String getNameKK() {
        return nameKK;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getName(int language) {
        if (language == Language.kk.getId()) {
            return nameKK;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public String getName(Language language) {
        if (language == Language.kk) {
            return nameKK;
        } else if (language == Language.ru) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

}
