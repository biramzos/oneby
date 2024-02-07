package com.web.oneby.commons.Repositories;

import com.web.oneby.commons.Enums.UserRole;
import com.web.oneby.commons.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Transactional
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    Page<User> findAll(Specification<User> specification, Pageable pageable);
    Integer countAllByRolesContaining(UserRole role);
}
