package com.web.oneby.modules.books.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Models.OBFile;
import com.web.oneby.modules.books.Enums.Genre;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title_kk")
    private String titleKK;
    @Column(name = "title_ru")
    private String titleRU;
    @Column(name = "title_en")
    private String titleEN;
    @Column(name = "description_kk")
    private String descriptionKK;
    @Column(name = "description_ru")
    private String descriptionRU;
    @Column(name = "description_en")
    private String descriptionEN;
    @Column(name = "author_kk")
    private String authorKK;
    @Column(name = "author_ru")
    private String authorRU;
    @Column(name = "author_en")
    private String authorEN;
    @JsonIgnore
    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    private List<Genre> genres;
    @JsonIgnore
    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "file_id")
    private OBFile file;

    public Book(String titleKK,
                String titleRU,
                String titleEN,
                String descriptionKK,
                String descriptionRU,
                String descriptionEN,
                String authorKK,
                String authorRU,
                String authorEN,
                List<Genre> genres,
                byte[] image,
                OBFile file) {
        this.titleKK = titleKK;
        this.titleRU = titleRU;
        this.titleEN = titleEN;
        this.descriptionKK = descriptionKK;
        this.descriptionRU = descriptionRU;
        this.descriptionEN = descriptionEN;
        this.authorKK = authorKK;
        this.authorRU = authorRU;
        this.authorEN = authorEN;
        this.genres = genres;
        this.image = image;
        this.file = file;
    }

    public String getTitle(int language) {
        Map<String, String> titles = new HashMap<>() {{
            put("titleKK", titleKK);
            put("titleRU", titleRU);
            put("titleEN", titleEN);
        }};
        return titles.get("title" + Language.getLanguageById(language).suffix());
    }

    public String getTitle(Language language) {
        Map<String, String> titles = new HashMap<>() {{
            put("titleKK", titleKK);
            put("titleRU", titleRU);
            put("titleEN", titleEN);
        }};
        return titles.get("title" + language.suffix());
    }

    public String getDescription(int language) {
        Map<String, String> descriptions = new HashMap<>() {{
            put("descriptionKK", descriptionKK);
            put("descriptionRU", descriptionRU);
            put("descriptionEN", descriptionEN);
        }};
        return descriptions.get("description" + Language.getLanguageById(language).suffix());
    }

    public String getDescription(Language language) {
        Map<String, String> descriptions = new HashMap<>() {{
            put("descriptionKK", descriptionKK);
            put("descriptionRU", descriptionRU);
            put("descriptionEN", descriptionEN);
        }};
        return descriptions.get("description" + language.suffix());
    }

    public String getAuthor(int language) {
        Map<String, String> authors = new HashMap<>() {{
            put("authorKK", authorKK);
            put("authorRU", authorRU);
            put("authorEN", authorEN);
        }};
        return authors.get("author" + Language.getLanguageById(language).suffix());
    }

    public String getAuthor(Language language) {
        Map<String, String> authors = new HashMap<>() {{
            put("authorKK", authorKK);
            put("authorRU", authorRU);
            put("authorEN", authorEN);
        }};
        return authors.get("author" + language.suffix());
    }
}
