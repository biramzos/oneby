package com.web.oneby.Repositories;

import com.web.oneby.DTO.UserSearchFilterRequest;
import com.web.oneby.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Transactional
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    @Query(value = "SELECT * FROM users WHERE (:#{#filter.name} IS NULL OR " +
            "(email LIKE %:#{#filter.name}% OR username LIKE %:#{#filter.name}%)) " +
            "AND (:#{#filter.roles.size()} = 0 OR role IN (:#{#filter.roles})) ",
            nativeQuery = true)
    Page<User> findAll(@Param("filter") UserSearchFilterRequest filter, Pageable pageable);
}
