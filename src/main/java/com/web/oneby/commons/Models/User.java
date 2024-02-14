package com.web.oneby.commons.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.books.Enums.Genre;
import com.web.oneby.books.Models.Book;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Table(name = "users")
@Entity
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_ru")
    private String nameRU;
    @Column(name = "lastname_ru")
    private String lastnameRU;
    @Column(name = "name_kz")
    private String nameKZ;
    @Column(name = "lastname_kz")
    private String lastnameKZ;
    @Column(name = "name_en")
    private String nameEN;
    @Column(name = "lastname_en")
    private String lastnameEN;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @Column(name = "token")
    private String token;
    @JsonIgnore
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserRole> roles = new HashSet<>();
    @JsonIgnore
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    @Column(name = "is_active")
    private boolean isActive;
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favourites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> favourites = new ArrayList<>();

    public User(
            String nameKZ,
            String nameRU,
            String nameEN,
            String lastnameKZ,
            String lastnameRU,
            String lastnameEN,
            String username,
            String email,
            String password,
            String token,
            Set<UserRole> roles,
            byte[] image,
            boolean isActive
    ){
        this.nameKZ = nameKZ;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.lastnameKZ = lastnameKZ;
        this.lastnameRU = lastnameRU;
        this.lastnameEN = lastnameEN;
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.roles = roles;
        this.image = image;
        this.isActive = isActive;
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

    public String getName(Language language) {
        if (language == Language.kz) {
            return nameKZ;
        } else if (language == Language.ru) {
            return nameRU;
        } else {
            return nameEN;
        }
    }

    public String getLastname(int language) {
        if (language == Language.kz.getId()) {
            return lastnameKZ;
        } else if (language == Language.ru.getId()) {
            return lastnameRU;
        } else {
            return lastnameEN;
        }
    }

    public String getLastname(Language language) {
        if (language == Language.kz) {
            return lastnameKZ;
        } else if (language == Language.ru) {
            return lastnameRU;
        } else {
            return lastnameEN;
        }
    }

    public boolean isPremium(){
        return this.roles.contains(UserRole.PREMIUM);
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isActive;
    }

}
