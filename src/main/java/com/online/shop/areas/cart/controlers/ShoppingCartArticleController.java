package com.online.shop.areas.cart.controlers;

import com.online.shop.areas.cart.dto.cart.ShoppingCartResponseDto;
import com.online.shop.areas.cart.services.ShoppingCartArticleService;
import com.online.shop.response.Response;
import com.online.shop.response.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/cartArticle")
public class ShoppingCartArticleController {

    private ShoppingCartArticleService shoppingCartArticleService;

    @Autowired
    public ShoppingCartArticleController(ShoppingCartArticleService shoppingCartArticleService) {
        this.shoppingCartArticleService = shoppingCartArticleService;
    }

    @RequestMapping(path = "/increase/{articleId}", method = RequestMethod.POST)
    public ResponseEntity<Response> increaseShoppingCartArticleQuantity(@PathVariable Long articleId){

        ShoppingCartResponseDto cartResponse = this.shoppingCartArticleService.increaseQuantityOfCartArticle(articleId);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.INCREASE_SHOPPING_CART_ARTICLE_SUCCESS, cartResponse), HttpStatus.OK);
    }

    @RequestMapping(path = "/decrease/{articleId}", method = RequestMethod.POST)
    public ResponseEntity<Response> decreaseShoppingCartArticleQuantity(@PathVariable Long articleId){

        ShoppingCartResponseDto cartResponse = this.shoppingCartArticleService.decreaseQuantityOfCartArticle(articleId);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.DECREASE_SHOPPING_CART_ARTICLE_SUCCESS, cartResponse), HttpStatus.OK);
    }

    @RequestMapping(path = "/remove/{articleId}", method = RequestMethod.POST)
    public ResponseEntity<Response> removeShoppingCartArticleById(@PathVariable Long articleId){

        ShoppingCartResponseDto cartResponse = this.shoppingCartArticleService.removeShoppingCartArticle(articleId);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.REMOVE_SHOPPING_CART_ARTICLE_SUCCESS, cartResponse), HttpStatus.OK);
    }




}
