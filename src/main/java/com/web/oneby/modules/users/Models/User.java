package com.web.oneby.modules.users.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Utils.ClassUtil;
import com.web.oneby.modules.users.Enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
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
    @Column(name = "name_kk")
    private String nameKK;
    @Column(name = "lastname_kk")
    private String lastnameKK;
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
    @JsonIgnore
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserRole> roles = new ArrayList<>();
    @JsonIgnore
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    @Column(name = "is_active", columnDefinition = "TINYINT")
    private boolean isActive;
//    @JsonIgnore
//    @JdbcTypeCode(SqlTypes.JSON)
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "user_favourites",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id")
//    )
//    private List<Book> favourites = new ArrayList<>();

    public User(
            String nameKK,
            String nameRU,
            String nameEN,
            String lastnameKK,
            String lastnameRU,
            String lastnameEN,
            String username,
            String email,
            String password,
            List<UserRole> roles,
            byte[] image,
            boolean isActive
    ){
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.lastnameKK = lastnameKK;
        this.lastnameRU = lastnameRU;
        this.lastnameEN = lastnameEN;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.image = image;
        this.isActive = isActive;
    }

//    public String getName(Language language) {
//        Map<String, String> names = new HashMap<>() {{
//            put(Language.kk.suffix(), nameKK);
//            put(Language.ru.suffix(), nameRU);
//            put(Language.en.suffix(), nameEN);
//        }};
//        return names.get(language.suffix());
//    }

    @SneakyThrows
    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

//    public String getLastname(Language language) {
//        Map<String, String> lastnames = new HashMap<>() {{
//            put(Language.kk.suffix(), lastnameKK);
//            put(Language.ru.suffix(), lastnameRU);
//            put(Language.en.suffix(), lastnameEN);
//        }};
//        return lastnames.get(language.suffix());
//    }

    @SneakyThrows
    public String getLastname(Language language) {
        Field field = User.class.getDeclaredField("lastname" + language.suffix());
        field.setAccessible(true);
        return (String) field.get(this);
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
