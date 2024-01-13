package com.web.oneby.Services;

import com.web.oneby.DTO.*;
import com.web.oneby.Enums.AccessBook;
import com.web.oneby.Enums.Genre;
import com.web.oneby.Models.Book;
import com.web.oneby.Models.User;
import com.web.oneby.Repositories.BookRepository;
import com.web.oneby.Utils.SortingUtils;
import com.web.oneby.Utils.StringUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService (
            BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    public Book add (BookRequest bookRequest, User publisher) throws IOException {
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
                bookRequest.getGenres().stream().map(Genre::valueOf).toList(),
                AccessBook.valueOf(bookRequest.getAccess()),
                bookRequest.getImage().getBytes(),
                bookRequest.getFile().getBytes()
            )
        );
    }

    public PageObject<BookResponse> findAll(SearchFilter request, int language) {
        List<Sort.Order> orders = SortingUtils.getSoringOrders(request.getSort());
        Pageable pageable = PageRequest.of(
                request.getPageNumber() - 1,
                request.getCountInPart(),
                Sort.by(orders));
        return new PageObject<>(bookRepository
                .findAll(BookService.filter(request.getFilter()), pageable)
                .map(book -> BookResponse.fromBook(book, language)));
    }


    public static Specification<Book> filter (Map<String, Object> filter) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("access"), AccessBook.PUBLIC.name());
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
