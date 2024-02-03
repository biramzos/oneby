package com.web.oneby.books.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.books.Enums.AccessBook;
import com.web.oneby.books.Enums.Genre;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Models.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "books")
@Entity
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title_kz", columnDefinition = "TEXT")
    private String titleKZ;
    @Column(name = "title_ru", columnDefinition = "TEXT")
    private String titleRU;
    @Column(name = "title_en", columnDefinition = "TEXT")
    private String titleEN;
    @Column(name = "description_kz", columnDefinition = "TEXT")
    private String descriptionKZ;
    @Column(name = "description_ru", columnDefinition = "TEXT")
    private String descriptionRU;
    @Column(name = "description_en", columnDefinition = "TEXT")
    private String descriptionEN;
    @Column(name = "author_kz")
    private String authorKZ;
    @Column(name = "author_ru")
    private String authorRU;
    @Column(name = "author_en")
    private String authorEN;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private User publisher;
    @Column(name = "year")
    private int year;
    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    private List<Genre> genres = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "access")
    private AccessBook access;
    @Column(name = "cost")
    private int cost;
    @Column(name = "stars")
    private int stars = 0;
    @Lob
    @JsonIgnore
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;
    @Lob
    @JsonIgnore
    @Column(name = "content", columnDefinition = "LONGBLOB")
    private byte[] content;
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="book_comments",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name="comment_id")
    )
    private List<Comment> comments = new ArrayList<>();

    public Book(
            String titleKZ,
            String titleRU,
            String titleEN,
            String descriptionKZ,
            String descriptionRU,
            String descriptionEN,
            String authorKZ,
            String authorRU,
            String authorEN,
            User publisher,
            int year,
            List<Genre> genres,
            AccessBook access,
            int cost,
            byte[] image,
            byte[] content
    ){
        this.titleKZ = titleKZ;
        this.titleRU = titleRU;
        this.titleEN = titleEN;
        this.descriptionKZ = descriptionKZ;
        this.descriptionRU = descriptionRU;
        this.descriptionEN = descriptionEN;
        this.authorKZ = authorKZ;
        this.authorRU = authorRU;
        this.authorEN = authorEN;
        this.publisher = publisher;
        this.year = year;
        this.genres = genres;
        this.access = access;
        this.cost = cost;
        this.image = image;
        this.content = content;
    }

    public String getTitle(int language) {
        if (language == Language.kz.getId()) {
            return titleKZ;
        } else if (language == Language.ru.getId()) {
            return titleRU;
        } else {
            return titleEN;
        }
    }

    public String getDescription(int language) {
        if (language == Language.kz.getId()) {
            return descriptionKZ;
        } else if (language == Language.ru.getId()) {
            return descriptionRU;
        } else {
            return descriptionEN;
        }
    }

    public String getAuthor(int language) {
        if (language == Language.kz.getId()) {
            return authorKZ;
        } else if (language == Language.ru.getId()) {
            return authorRU;
        } else {
            return authorEN;
        }
    }
}
