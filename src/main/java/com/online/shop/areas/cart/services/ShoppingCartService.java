package com.online.shop.areas.cart.services;

import com.online.shop.areas.cart.dto.cart.ShoppingCartResponseDto;
import com.online.shop.areas.cart.entities.ShoppingCart;
import com.online.shop.areas.cart.models.CreateBasketArticleBindingModel;

public interface ShoppingCartService {

    ShoppingCart findShoppingCartById(Long id);

    ShoppingCartResponseDto findShoppingCartByOwner(Long userId);

    ShoppingCartResponseDto addShoppingCartArticle(CreateBasketArticleBindingModel basketArticleModel);

    ShoppingCartResponseDto clearAllShoppingCartArticles(Long shoppingCartId);

    ShoppingCartResponseDto findShoppingCartResponseById(Long shoppingCartId);

}
