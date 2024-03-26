package com.web.oneby.modules.books.Repositories;

import com.web.oneby.modules.books.Models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = {"genres"})
    Page<Book> findAll(Specification<Book> specification, Pageable pageable);
}
