package com.web.oneby.commons.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.books.Models.Book;
import com.web.oneby.commons.Enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Table(name = "users")
@Entity
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserRole> roles;
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
            String username,
            String email,
            String password,
            String token,
            List<UserRole> roles,
            byte[] image,
            boolean isActive
    ){
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.roles = roles;
        this.image = image;
        this.isActive = isActive;
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
