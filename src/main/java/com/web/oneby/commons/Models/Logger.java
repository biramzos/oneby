package com.web.oneby.commons.Models;


import com.web.oneby.commons.Enums.LogType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "logs")
@NoArgsConstructor
public class Logger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LogType type;
    @Column(columnDefinition = "TEXT")
    private String message;

    public Logger(LogType type, String text) {
        this.type = type;
        this.message = text;
    }
}
