package com.web.oneby.modules.books.Enums;

import com.web.oneby.commons.Enums.Language;

import java.util.HashMap;
import java.util.Map;

public enum Genre {
    ACTION("Аракет", "Экшен", "Action"),
    ADVENTURE("Масштаб", "Приключения", "Adventure"),
    COMEDY("Көңіл көтеру", "Комедия", "Comedy"),
    DRAMA("Драма", "Драма", "Drama"),
    FANTASY("Фентези", "Фэнтези", "Fantasy"),
    HORROR("Қорғаныш", "Ужасы", "Horror"),
    MYSTERY("Мұра", "Мистика", "Mystery"),
    ROMANCE("Роман", "Романтика", "Romance"),
    SCIENCE_FICTION("Ғылым менән фантастика", "Научная фантастика", "Science Fiction"),
    THRILLER("Қатерлі", "Триллер", "Thriller"),
    WESTERN("Батыс", "Вестерн", "Western"),
    CRIME("Қылмыс", "Криминал", "Crime"),
    HISTORICAL("Тарихи", "Исторический", "Historical"),
    BIOGRAPHY("Биография", "Биографический", "Biography"),
    POETRY("Поэзия", "Поэзия", "Poetry");

    private final String nameKK;
    private final String nameRU;
    private final String nameEN;

    Genre(String nameKK, String nameRU, String nameEN) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

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
