package com.web.oneby.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.Enums.Language;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "rooms")
@Entity
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_kz")
    private String nameKZ;
    @Column(name = "name_ru")
    private String nameRU;
    @Column(name = "name_en")
    private String nameEN;
    @JdbcTypeCode(SqlTypes.JSON)
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "is_private")
    private boolean isPrivate;
    @Column(name = "password")
    private String password;
    @JdbcTypeCode(SqlTypes.JSON)
    @JoinColumn(name = "creator_id")
    private User creator;
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rooms_participants",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<User> participants = new HashSet<>();

    public Room (
            String nameKZ,
            String nameRU,
            String nameEN,
            Book book,
            boolean isPrivate,
            String password,
            User creator
    ) {
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.book = book;
        this.isPrivate = isPrivate;
        this.password = password;
        this.creator = creator;
    }

    public String getName(int language){
        if (language == Language.kz.getId()) {
            return nameKZ;
        } else if (language == Language.ru.getId()) {
            return nameRU;
        } else {
            return nameEN;
        }
    }
}
