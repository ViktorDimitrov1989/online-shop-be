package com.online.shop.areas.cart.dto.cart;

import com.online.shop.areas.cart.dto.cartArticle.ShoppingCartArticleResponseDto;

import java.util.List;

public class ShoppingCartResponseDto {

    private List<ShoppingCartArticleResponseDto> articles;

    public ShoppingCartResponseDto() {
    }

    public List<ShoppingCartArticleResponseDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ShoppingCartArticleResponseDto> articles) {
        this.articles = articles;
    }
}
