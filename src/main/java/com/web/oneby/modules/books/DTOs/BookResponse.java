package com.web.oneby.modules.books.DTOs;

import com.web.oneby.commons.Enums.Language;
import com.web.oneby.modules.books.Models.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String description;
    private String author;
    private List<String> genres = new ArrayList<>();
    private String image;
    private String file;

    public static BookResponse fromBook(Book book, int language) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.id = book.getId();
        bookResponse.title = book.getTitle(language);
        bookResponse.description = book.getDescription(language);
        bookResponse.author = book.getAuthor(language);
        bookResponse.genres = book.getGenres().stream().map((role) -> role.getName(language)).toList();
        bookResponse.image = "/api/v1/books/poster/" + book.getId();
        bookResponse.file = "/api/v1/books/file/" + book.getId();
        return bookResponse;
    }

    public static BookResponse fromBook(Book book, Language language) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.id = book.getId();
        bookResponse.title = book.getTitle(language);
        bookResponse.description = book.getDescription(language);
        bookResponse.author = book.getAuthor(language);
        bookResponse.genres = book.getGenres().stream().map((role) -> role.getName(language)).toList();
        bookResponse.image = "/api/v1/books/poster/" + book.getId();
        bookResponse.file = "/api/v1/books/file/" + book.getId();
        return bookResponse;
    }
}
