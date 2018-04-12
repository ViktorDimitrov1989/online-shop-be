package com.online.shop.areas.cart.serviceImpl;

import com.online.shop.areas.cart.dto.cart.ShoppingCartResponseDto;
import com.online.shop.areas.cart.entities.ShoppingCartArticle;
import com.online.shop.areas.cart.repositories.ShoppingCartArticleRepository;
import com.online.shop.areas.cart.services.ShoppingCartArticleService;
import com.online.shop.areas.cart.services.ShoppingCartService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartArticleServiceImpl implements ShoppingCartArticleService {


    private ShoppingCartArticleRepository shoppingCartArticleRepository;

    private ShoppingCartService shoppingCartService;

    private ModelMapper modelMapper;

    @Autowired
    public ShoppingCartArticleServiceImpl(ShoppingCartArticleRepository shoppingCartArticleRepository,
                                          ShoppingCartService shoppingCartService,
                                          ModelMapper modelMapper) {
        this.shoppingCartArticleRepository = shoppingCartArticleRepository;
        this.shoppingCartService = shoppingCartService;
        this.modelMapper = modelMapper;
    }


    @Override
    public ShoppingCartResponseDto removeShoppingCartArticle(Long shoppingCartArticleId) {
        ShoppingCartArticle articleToRemove = this.shoppingCartArticleRepository.findOneById(shoppingCartArticleId);

        if(articleToRemove == null){
            throw new RequestException(ResponseMessageConstants.INVALID_SHOPPING_CART_ARTICLE_ID, HttpStatus.BAD_REQUEST);
        }

        this.shoppingCartArticleRepository.delete(articleToRemove);

        ShoppingCartResponseDto cartResponse = this.shoppingCartService
                .findShoppingCartResponseById(articleToRemove.getShoppingCart().getId());

        return cartResponse;
    }

    @Override
    public ShoppingCartResponseDto increaseQuantityOfCartArticle(Long shoppingCartArticleId) {
        ShoppingCartArticle articleToEdit = this.shoppingCartArticleRepository.findOneById(shoppingCartArticleId);

        if(articleToEdit == null){
            throw new RequestException(ResponseMessageConstants.INVALID_SHOPPING_CART_ARTICLE_ID, HttpStatus.BAD_REQUEST);
        }

        articleToEdit.setQuantity(articleToEdit.getQuantity() + 1);

        this.shoppingCartArticleRepository.save(articleToEdit);

        ShoppingCartResponseDto cartResponse = this.shoppingCartService
                .findShoppingCartResponseById(articleToEdit.getShoppingCart().getId());

        return cartResponse;
    }

    @Override
    public ShoppingCartResponseDto decreaseQuantityOfCartArticle(Long shoppingCartArticleId) {
        ShoppingCartArticle articleToEdit = this.shoppingCartArticleRepository.findOneById(shoppingCartArticleId);

        if(articleToEdit == null){
            throw new RequestException(ResponseMessageConstants.INVALID_SHOPPING_CART_ARTICLE_ID, HttpStatus.BAD_REQUEST);
        }
        articleToEdit.setQuantity(articleToEdit.getQuantity() - 1);

        this.shoppingCartArticleRepository.save(articleToEdit);

        ShoppingCartResponseDto cartResponse = this.shoppingCartService
                .findShoppingCartResponseById(articleToEdit.getShoppingCart().getId());

        return cartResponse;
    }
}
