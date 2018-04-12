package com.online.shop.areas.cart.repositories;

import com.online.shop.areas.cart.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

    ShoppingCart findOneByOwnerId(Long userId);

}
