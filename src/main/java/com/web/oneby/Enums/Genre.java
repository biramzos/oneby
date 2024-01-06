package com.web.oneby.Enums;

public enum Genre {

    FANTASY(1, "Фэнтези", "Фэнтези", "Fantasy"),
    SCIENCE_FICTION(2, "Ғылыми фантастика", "Научная фантастика", "Science fiction"),
    REALISM(3, "Реализм", "Реализм", "Realism"),
    ROMANCE(4, "Роман", "Роман", "Romance"),
    DETECTIVE(5, "Детектив", "Детектив", "Detective"),
    THRILLER(6, "Триллер", "Триллер", "Thriller"),
    HORROR(7, "Киімділік", "Ужасы", "Horror"),
    ADVENTURE(8, "Мактаптау", "Приключения", "Adventure"),
    HISTORICAL_FICTION(9, "Тарихи роман", "Исторический роман", "Historical Fiction"),
    PHILOSOPHY(10, "Философия", "Философия", "Philosophy"),
    PSYCHOLOGY(11, "Психологиялық", "Психологические", "Psychological"),
    BIOGRAPHY(12, "Биография", "Биография", "Biography"),
    HUMOR(13, "Күлкілік", "Юмор", "Humor"),
    DRAMA(14, "Драма", "Драма", "Drama"),
    POETRY(15, "Поэзия", "Поэзия", "Poetry"),
    EDUCATIONAL(16, "Білім біру", "Учебные", "Educational"),
    OTHER(17, "Баскалары", "Другие", "Others"),

    ;
    Genre(int id, String nameKZ, String nameRU, String nameEN){
        this.id = id;
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    private int id;
    private String nameKZ;
    private String nameRU;
    private String nameEN;

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

}
