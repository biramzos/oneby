package com.web.oneby.modules.books.Services;

import com.web.oneby.commons.DTOs.PageObject;
import com.web.oneby.commons.DTOs.SearchFilter;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Handlers.HTTPMessageHandler;
import com.web.oneby.commons.Utils.SortingUtil;
import com.web.oneby.modules.books.DTOs.BookRequest;
import com.web.oneby.modules.books.DTOs.BookResponse;
import com.web.oneby.modules.books.Models.Book;
import com.web.oneby.modules.books.Repositories.BookRepository;
import com.web.oneby.modules.books.Specifications.BookSpecification;
import com.web.oneby.modules.users.DTOs.UserResponse;
import com.web.oneby.modules.users.Specifications.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService (BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public PageObject<BookResponse> search(SearchFilter request, Language language) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber() - 1,
                request.getCountInPage(),
                Sort.by(SortingUtil.getSortingOrders(request.getSort(), language)));
        return new PageObject<>(bookRepository
                .findAll(BookSpecification.getSpecification(request.getFilter()), pageable)
                .map(book -> BookResponse.fromBook(book, language)));
    }

}
