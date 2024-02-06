package com.web.oneby.books.DTOs;

import com.web.oneby.books.Models.Book;
import com.web.oneby.commons.DTOs.UserResponse;
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
    private String access;
    private List<String> genres = new ArrayList<>();
    private UserResponse publisher;

    public static BookResponse fromBook(Book book, int language){
        BookResponse bookResponse = new BookResponse();
        bookResponse.id = book.getId();
        bookResponse.title = book.getTitle(language);
        bookResponse.description = book.getDescription(language);
        bookResponse.author = book.getAuthor(language);
        bookResponse.access = book.getAccess().getName(language);
        bookResponse.genres = book.getGenres().stream().map((genre) -> genre.getName(language)).toList();
        bookResponse.publisher = UserResponse.fromUser(book.getPublisher(), language);
        return bookResponse;
    }

}