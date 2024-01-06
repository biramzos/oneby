package com.web.oneby.Services;

import com.web.oneby.DTO.BookRequest;
import com.web.oneby.DTO.BookResponse;
import com.web.oneby.DTO.BookSearchFilterRequest;
import com.web.oneby.DTO.PageObject;
import com.web.oneby.Models.Book;
import com.web.oneby.Models.User;
import com.web.oneby.Repositories.BookRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService (
            BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    @SneakyThrows
    public Book add (BookRequest bookRequest, User publisher) {
        return bookRepository.save(
            new Book(
                bookRequest.getNameKZ(),
                bookRequest.getNameRU(),
                bookRequest.getNameEN(),
                bookRequest.getDescriptionKZ(),
                bookRequest.getDescriptionRU(),
                bookRequest.getDescriptionEN(),
                bookRequest.getAuthorKZ(),
                bookRequest.getAuthorRU(),
                bookRequest.getAuthorEN(),
                publisher,
                bookRequest.getYear(),
                bookRequest.getImage().getBytes(),
                bookRequest.getFile().getBytes()
            )
        );
    }

    public PageObject<BookResponse> findAll(BookSearchFilterRequest request, int sizeInPart, Integer pageNumber, int language) {
        return new PageObject<>(bookRepository.findAll(request, Pageable.ofSize(sizeInPart).withPage(pageNumber)).map(book -> BookResponse.fromBook(book, language)));
    }
}
