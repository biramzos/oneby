package com.web.oneby.modules.users.Repositories;

import com.web.oneby.modules.users.Enums.UserRole;
import com.web.oneby.modules.users.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Transactional
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(String username);
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByToken(String token);
    @EntityGraph(attributePaths = {"roles"})
    Page<User> findAll(Specification<User> specification, Pageable pageable);
    @EntityGraph(attributePaths = {"roles"})
    Integer countAllByRolesContaining(UserRole role);
}
