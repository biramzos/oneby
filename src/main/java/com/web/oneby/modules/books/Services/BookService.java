package com.web.oneby.modules.books.Services;

import com.web.oneby.modules.books.DTOs.BookRequest;
import com.web.oneby.modules.books.DTOs.BookResponse;
import com.web.oneby.modules.books.Enums.AccessBook;
import com.web.oneby.modules.books.Models.Book;
import com.web.oneby.modules.books.Repositories.BookRepository;
import com.web.oneby.commons.DTOs.PageObject;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Utils.StringUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.web.oneby.commons.DTOs.SearchFilter;
import com.web.oneby.commons.Utils.SortingUtil;
import com.web.oneby.commons.Handlers.HTTPMessageHandler;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BookService {

    private BookRepository bookRepository;
    private OrganizationService organizationService;

    @Autowired
    public BookService (
            BookRepository bookRepository,
            OrganizationService organizationService
    ) {
        this.bookRepository = bookRepository;
        this.organizationService = organizationService;
    }

    public Book getById(Long bookId){
        return bookRepository.getById(bookId);
    }

    public Book save (BookRequest bookRequest, User publisher) throws IOException {
        Book book;
        if (bookRequest.getId() > 0) {
            book = bookRepository.getById(bookRequest.getId());
            if (book.getOrganization().getEmployees().contains(publisher)) {
                book.setTitleKZ(bookRequest.getNameKK());
                book.setTitleRU(bookRequest.getNameRU());
                book.setTitleEN(bookRequest.getNameEN());
                book.setDescriptionKZ(bookRequest.getDescriptionKZ());
                book.setDescriptionRU(bookRequest.getDescriptionRU());
                book.setDescriptionEN(bookRequest.getDescriptionEN());
                book.setAuthorKZ(bookRequest.getAuthorKZ());
                book.setAuthorRU(bookRequest.getAuthorRU());
                book.setAuthorEN(bookRequest.getAuthorEN());
                book.setYear(bookRequest.getYear());
                book.setGenres(bookRequest.getGenres());
                book.setAccess(bookRequest.getAccess());
                book.setCost(bookRequest.getCost());
                book.setImage(bookRequest.getImage().getBytes());
                book.setContent(bookRequest.getFile().getBytes());
            } else {
                throw new AccessDeniedException("User '" + publisher.getUsername() + "' can not access to edit this book!");
            }
        } else {
            if (organizationService.getOrganizationByUser(publisher) != null){
                book = new Book(
                        bookRequest.getNameKK(),
                        bookRequest.getNameRU(),
                        bookRequest.getNameEN(),
                        bookRequest.getDescriptionKZ(),
                        bookRequest.getDescriptionRU(),
                        bookRequest.getDescriptionEN(),
                        bookRequest.getAuthorKZ(),
                        bookRequest.getAuthorRU(),
                        bookRequest.getAuthorEN(),
                        organizationService.getOrganizationByUser(publisher),
                        bookRequest.getYear(),
                        bookRequest.getGenres(),
                        bookRequest.getAccess(),
                        bookRequest.getCost(),
                        bookRequest.getImage().getBytes(),
                        bookRequest.getFile().getBytes()
                );
            } else {
                throw new AccessDeniedException("User '" + publisher.getUsername() + "' is not employee!");
            }
        }
        return bookRepository.save(
            book
        );
    }



    public PageObject<BookResponse> findAll(SearchFilter request, User user, int language) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber() - 1,
                request.getCountInPage(),
                Sort.by(SortingUtil.getSortingOrders(request.getSort())));
        return new PageObject<>(bookRepository
                .findAll(BookService.filter(request.getFilter(), user), pageable)
                .map(book -> BookResponse.fromBook(book, language)));
    }

    public boolean delete(Book book, HTTPMessageHandler messageHandler, int language){
        if (book == null) {
            messageHandler.set(HTTPMessage.BOOK_IS_NOT_DELETED, language);
            return false;
        }
        bookRepository.delete(book);
        messageHandler.set(HTTPMessage.BOOK_IS_DELETED, language);
        return true;
    }


    public static Specification<Book> filter (Map<String, Object> filter, User user) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.or(
                criteriaBuilder.equal(root.join("users").get("username"), user.getUsername()),
                criteriaBuilder.and(
                    criteriaBuilder.notEqual(root.join("users").get("username"), user.getUsername()),
                    criteriaBuilder.equal(root.get("access"), AccessBook.PUBLIC.name())
                )
            );
            for (String key: filter.keySet()) {
                Object value = filter.get(key);
                if (key.equals("name") && StringUtil.isNotEmpty((String) value)) {
                    predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.or(
                            criteriaBuilder.like(root.get("name_kz"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("name_ru"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("name_en"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("description_kz"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("description_ru"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("description_en"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("author_kz"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("author_ru"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("author_en"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.join("users").get("username"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.join("users").get("email"), "%" + (String) value + "%")
                        )
                    );
                }

                if (key.equals("year") && (Integer) value != 0) {
                    predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(root.get("year"), (Integer) value)
                    );
                }

                if (key.equals("stars") && (Integer) value != -1) {
                    predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(root.get("stars"), (Integer) value)
                    );
                }

                if (key.equals("genres") && !(((List<String>) value).isEmpty())) {
                    predicate = criteriaBuilder.and(
                        predicate,
                        root.join("genres").in((List<String>) value)
                    );
                }
            }
            return predicate;
        });
    }
}
