package com.web.oneby.books.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.books.Enums.OrganizationStatus;
import com.web.oneby.commons.Models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrganizationStatus status = OrganizationStatus.APPLIED;
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "organization_books",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();
    @JsonIgnore
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    public Organization (
            String name,
            byte[] image,
            User user
    ) {
        this.name = name;
        this.image = image;
        this.user = user;
    }
}
