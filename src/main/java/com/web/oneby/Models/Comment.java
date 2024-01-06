package com.web.oneby.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Table(name = "comments")
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JdbcTypeCode(SqlTypes.JSON)
    @JoinColumn(name = "user_id")
    private User sender;
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
    @Column(name = "date_time", columnDefinition = "DATETIME")
    private LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Almaty"));

    public Comment (
            User sender,
            String comment
    ) {
        this.sender = sender;
        this.comment = comment;
    }
}
