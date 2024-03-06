package com.web.oneby.modules.books.Repositories;

import com.web.oneby.modules.books.Models.Book;
import com.web.oneby.modules.books.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByBooksContains(Book book);
    Optional<Organization> findByName(String name);
    Optional<Organization> findOrganizationByEmployeesContains(User employee);
    Optional<Organization> findByUser(User user);
}
