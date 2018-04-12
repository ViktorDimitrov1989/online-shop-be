package com.online.shop.areas.cart.controlers;

import com.online.shop.areas.cart.dto.cart.ShoppingCartResponseDto;
import com.online.shop.areas.cart.services.ShoppingCartService;
import com.online.shop.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @RequestMapping(path = "/get/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Response> findCartByUserId(@PathVariable Long userId){

        ShoppingCartResponseDto cartResponse = this.shoppingCartService.findShoppingCartByOwner(userId);

        return new ResponseEntity<>(new Response("", cartResponse), HttpStatus.OK);
    }
    

}
