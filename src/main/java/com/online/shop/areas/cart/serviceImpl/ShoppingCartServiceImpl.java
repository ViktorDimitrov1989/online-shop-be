package com.online.shop.areas.cart.serviceImpl;

import com.online.shop.areas.cart.dto.cart.ShoppingCartResponseDto;
import com.online.shop.areas.cart.entities.ShoppingCart;
import com.online.shop.areas.cart.repositories.ShoppingCartRepository;
import com.online.shop.areas.cart.services.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCartResponseDto findShoppingCartByOwner(Long userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findOneByOwnerId(userId);

        ShoppingCartResponseDto response = this.modelMapper.map(shoppingCart, ShoppingCartResponseDto.class);

        return response;
    }
}
