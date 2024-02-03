package com.web.oneby.books.Repositories;

import com.web.oneby.books.Models.Book;
import com.web.oneby.books.Models.Organization;
import com.web.oneby.commons.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByBooksContains(Book book);
    Optional<Organization> findByName(String name);
    Optional<Organization> findByUser(User user);
}
