package com.web.oneby.modules.books.Enums;

import com.web.oneby.commons.Enums.Language;

import java.util.HashMap;
import java.util.Map;

public enum Genre {
    ACTION(1, "Аракет", "Экшен", "Action"),
    ADVENTURE(2, "Масштаб", "Приключения", "Adventure"),
    COMEDY(3, "Көңіл көтеру", "Комедия", "Comedy"),
    DRAMA(4, "Драма", "Драма", "Drama"),
    FANTASY(5, "Фентези", "Фэнтези", "Fantasy"),
    HORROR(6, "Қорғаныш", "Ужасы", "Horror"),
    MYSTERY(7, "Мұра", "Мистика", "Mystery"),
    ROMANCE(8, "Роман", "Романтика", "Romance"),
    SCIENCE_FICTION(9, "Ғылым менән фантастика", "Научная фантастика", "Science Fiction"),
    THRILLER(10, "Қатерлі", "Триллер", "Thriller"),
    WESTERN(11, "Батыс", "Вестерн", "Western"),
    CRIME(12, "Қылмыс", "Криминал", "Crime"),
    HISTORICAL(13, "Тарихи", "Исторический", "Historical"),
    BIOGRAPHY(14, "Биография", "Биографический", "Biography"),
    POETRY(15, "Поэзия", "Поэзия", "Poetry");

    private final int id;
    private final String nameKK;
    private final String nameRU;
    private final String nameEN;

    private final static Map<Integer, Genre> genres = new HashMap<>();

    static {
        for (Genre genre: values()) {
            genres.put(genre.id, genre);
        }
    }

    Genre(int id, String nameKK, String nameRU, String nameEN) {
        this.id = id;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public static Genre getByID(int id) {
        return genres.get(id);
    }

    public int getId() {
        return id;
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
