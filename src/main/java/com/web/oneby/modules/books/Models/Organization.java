package com.web.oneby.modules.books.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.oneby.modules.books.Enums.OrganizationStatus;
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
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "organization_employees",
        joinColumns = @JoinColumn(name = "organization_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<User> employees;
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organization")
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
