package com.web.oneby.modules.books.Repositories;

import com.web.oneby.modules.books.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
