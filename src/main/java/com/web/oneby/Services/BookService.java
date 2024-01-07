package com.web.oneby.Services;

import com.web.oneby.DTO.BookRequest;
import com.web.oneby.DTO.BookResponse;
import com.web.oneby.DTO.BookSearchFilterRequest;
import com.web.oneby.DTO.PageObject;
import com.web.oneby.Enums.Genre;
import com.web.oneby.Models.Book;
import com.web.oneby.Models.User;
import com.web.oneby.Repositories.BookRepository;
import com.web.oneby.Utils.StringUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
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
        return new PageObject<>(bookRepository.findAll(BookService.filter(request), Pageable.ofSize(sizeInPart).withPage(pageNumber)).map(book -> BookResponse.fromBook(book, language)));
    }


    public static Specification<Book> filter (BookSearchFilterRequest request) {
        return ((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
//            @Query(value = "SELECT b.* FROM books b JOIN users u ON b.publisher = u.id JOIN book_genres bg on b.id = bg.book_id WHERE (:#{#filter.name} IS NULL " +
//                    " OR (b.title_en LIKE %:#{#filter.name}% OR b.title_ru LIKE %:#{#filter.name}% OR b.title_kz LIKE %:#{#filter.name}%) " +
//                    " OR (b.description_en LIKE %:#{#filter.name}% OR b.description_ru LIKE %:#{#filter.name}% OR b.description_kz LIKE %:#{#filter.name}%) " +
//                    " OR (b.author_en LIKE %:#{#filter.name}% OR b.author_ru LIKE %:#{#filter.name}% OR b.author_kz LIKE %:#{#filter.name}%) " +
//                    " OR (u.username LIKE %:#{#filter.name}% OR u.email LIKE %:#{#filter.name}%)) AND (:#{#filter.year} IS NULL OR (b.year = :#{#filter.year})) " +
//                    " AND (:#{#filter.stars} IS NULL OR (:#{#filter.year} = b.stars)) AND (:#{#filter.genres.size()} = 0 OR (:#{#bg.genre_id} IN :#{#filter.genres}))",
//                    nativeQuery = true)
            if (StringUtil.isNotEmpty(request.getName())) {
                predicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name_kz"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.get("name_ru"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.get("name_en"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.get("description_kz"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.get("description_ru"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.get("description_en"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.get("author_kz"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.get("author_ru"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.get("author_en"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.join("users").get("username"), "%" + request.getName() + "%"),
                    criteriaBuilder.like(root.join("users").get("email"), "%" + request.getName() + "%")
                );
            }

            if (request.getYear() != 0) {
                predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(root.get("year"), request.getYear())
                );
            }

            if (request.getStars() != -1) {
                predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(root.get("stars"), request.getYear())
                );
            }

            if (!(request.getGenres().isEmpty())) {
                predicate = criteriaBuilder.and(
                    predicate,
                    root.join("genres").in(request.getGenres())
                );
            }

            return predicate;
        });
    }
}
