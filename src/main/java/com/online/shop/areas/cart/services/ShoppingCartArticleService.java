package com.online.shop.areas.cart.services;

import com.online.shop.areas.cart.dto.cart.ShoppingCartResponseDto;

public interface ShoppingCartArticleService {

    ShoppingCartResponseDto increaseQuantityOfCartArticle(Long shoppingCartArticleId);

    ShoppingCartResponseDto removeShoppingCartArticle(Long shoppingCartArticleId);

    ShoppingCartResponseDto decreaseQuantityOfCartArticle(Long shoppingCartArticleId);
}
