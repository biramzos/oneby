package com.web.oneby.modules.books.Repositories;

import com.web.oneby.modules.books.Models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
}
