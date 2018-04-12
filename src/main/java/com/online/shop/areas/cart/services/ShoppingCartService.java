package com.online.shop.areas.cart.services;

import com.online.shop.areas.cart.dto.cart.ShoppingCartResponseDto;

public interface ShoppingCartService {

    ShoppingCartResponseDto findShoppingCartByOwner(Long userId);

}
