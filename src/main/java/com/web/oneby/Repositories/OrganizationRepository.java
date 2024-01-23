package com.web.oneby.Repositories;

import com.web.oneby.Models.Book;
import com.web.oneby.Models.Organization;
import com.web.oneby.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByBooksContains(Book book);
    Optional<Organization> findByName(String name);
    Optional<Organization> findByUser(User user);
}
