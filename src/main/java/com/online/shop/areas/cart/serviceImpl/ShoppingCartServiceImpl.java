package com.online.shop.areas.cart.serviceImpl;

import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.entities.Color;
import com.online.shop.areas.articles.entities.Size;
import com.online.shop.areas.articles.services.ArticleService;
import com.online.shop.areas.articles.services.ColorService;
import com.online.shop.areas.articles.services.SizeService;
import com.online.shop.areas.cart.dto.cart.ShoppingCartResponseDto;
import com.online.shop.areas.cart.entities.ShoppingCart;
import com.online.shop.areas.cart.entities.ShoppingCartArticle;
import com.online.shop.areas.cart.models.CreateBasketArticleBindingModel;
import com.online.shop.areas.cart.repositories.ShoppingCartRepository;
import com.online.shop.areas.cart.services.ShoppingCartService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    private ArticleService articleService;

    private SizeService sizeService;

    private ColorService colorService;

    private ModelMapper modelMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   ArticleService articleService,
                                   SizeService sizeService,
                                   ColorService colorService,
                                   ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.articleService = articleService;
        this.sizeService = sizeService;
        this.colorService = colorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCart findShoppingCartById(Long id) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findOneById(id);

        if(shoppingCart == null){
            throw new RequestException(ResponseMessageConstants.INVALID_CART_ID,HttpStatus.BAD_REQUEST);
        }

        return shoppingCart;
    }

    @Override
    public ShoppingCartResponseDto findShoppingCartByOwner(Long userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findOneByOwnerId(userId);

        ShoppingCartResponseDto response = this.modelMapper.map(shoppingCart, ShoppingCartResponseDto.class);

        return response;
    }

    @Override
    public ShoppingCartResponseDto addShoppingCartArticle(CreateBasketArticleBindingModel basketArticleModel) {
        Article chosenArticle = this.articleService.getArticleById(basketArticleModel.getArticleId());
        Color chosenColor = this.colorService.findColorByName(basketArticleModel.getColor());
        Size chosenSize = this.sizeService.findSizeByName(basketArticleModel.getSize());

        ShoppingCartArticle articleToSave = new ShoppingCartArticle(chosenArticle,chosenSize,chosenColor,basketArticleModel.getQuantity());
        ShoppingCart cartToUpdate = this.findShoppingCartById(basketArticleModel.getShoppingCartId());


        if(cartToUpdate.getArticles().contains(articleToSave)){
            throw new RequestException(ResponseMessageConstants.EXISTING_SHOPPING_CAR_ARTICLE, HttpStatus.BAD_REQUEST);
        }

        cartToUpdate.getArticles().add(articleToSave);
        articleToSave.setShoppingCart(cartToUpdate);

        ShoppingCart shoppingCart = this.shoppingCartRepository.save(cartToUpdate);

        ShoppingCartResponseDto response = this.modelMapper.map(shoppingCart, ShoppingCartResponseDto.class);

        return response;
    }

    @Override
    public ShoppingCartResponseDto clearAllShoppingCartArticles(Long shoppingCartId) {
        ShoppingCart cartToUpdate = this.findShoppingCartById(shoppingCartId);

        cartToUpdate.getArticles().clear();

        ShoppingCart clearedCart = this.shoppingCartRepository.save(cartToUpdate);

        ShoppingCartResponseDto response = this.modelMapper.map(clearedCart, ShoppingCartResponseDto.class);

        return response;
    }

    @Override
    public ShoppingCartResponseDto findShoppingCartResponseById(Long shoppingCartId) {
        ShoppingCart cart = this.findShoppingCartById(shoppingCartId);

        ShoppingCartResponseDto response = this.modelMapper.map(cart, ShoppingCartResponseDto.class);

        return response;
    }

}
