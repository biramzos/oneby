package com.web.oneby.books.Models;

import com.web.oneby.books.Enums.BasketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "baskets")
@Getter
@Setter
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String time = LocalDateTime.now(ZoneId.of("Asia/Almaty")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "basket_books",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BasketStatus status = BasketStatus.CREATED;
    @Column
    private Double total;

}
