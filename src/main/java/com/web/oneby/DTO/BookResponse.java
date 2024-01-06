package com.web.oneby.DTO;

import com.web.oneby.Models.Book;
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
    private UserResponse publisher;

    public static BookResponse fromBook(Book book, int language){
        BookResponse bookResponse = new BookResponse();
        bookResponse.id = book.getId();
        bookResponse.title = book.getTitle(language);
        bookResponse.description = book.getDescription(language);
        bookResponse.author = book.getAuthor(language);
        bookResponse.genres = book.getGenres().stream().map((genre) -> genre.getName(language)).toList();
        bookResponse.publisher = UserResponse.fromUser(book.getPublisher(), language);
        return bookResponse;
    }

}
