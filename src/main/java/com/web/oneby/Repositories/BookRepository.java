package com.web.oneby.Repositories;

import com.web.oneby.DTO.BookSearchFilterRequest;
import com.web.oneby.Models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookById(Long id);
    @Query(value = "SELECT b.* FROM books b JOIN users u ON b.publisher = u.id JOIN book_genres bg on b.id = bg.book_id WHERE (:#{#filter.name} IS NULL " +
            " OR (b.title_en LIKE %:#{#filter.name}% OR b.title_ru LIKE %:#{#filter.name}% OR b.title_kz LIKE %:#{#filter.name}%) " +
            " OR (b.description_en LIKE %:#{#filter.name}% OR b.description_ru LIKE %:#{#filter.name}% OR b.description_kz LIKE %:#{#filter.name}%) " +
            " OR (b.author_en LIKE %:#{#filter.name}% OR b.author_ru LIKE %:#{#filter.name}% OR b.author_kz LIKE %:#{#filter.name}%) " +
            " OR (u.username LIKE %:#{#filter.name}% OR u.email LIKE %:#{#filter.name}%)) AND (:#{#filter.year} IS NULL OR (b.year = :#{#filter.year})) " +
            " AND (:#{#filter.stars} IS NULL OR (:#{#filter.year} = b.stars)) AND (:#{#filter.genres.size()} = 0 OR (:#{#bg.genre_id} IN :#{#filter.genres}))",
            nativeQuery = true)
    Page<Book> findAll(@Param("filter") BookSearchFilterRequest request, Pageable pageable);

}
