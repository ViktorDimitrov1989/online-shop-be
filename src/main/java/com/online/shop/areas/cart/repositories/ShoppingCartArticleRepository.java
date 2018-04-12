package com.online.shop.areas.cart.repositories;

import com.online.shop.areas.cart.entities.ShoppingCartArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartArticleRepository extends JpaRepository<ShoppingCartArticle, Long> {
}
