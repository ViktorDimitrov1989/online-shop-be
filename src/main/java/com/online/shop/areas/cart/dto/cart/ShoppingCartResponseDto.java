package com.online.shop.areas.cart.dto.cart;

import com.online.shop.areas.cart.dto.cartArticle.ShoppingCartArticleResponseDto;

import java.util.List;

public class ShoppingCartResponseDto {

    private Long id;

    private List<ShoppingCartArticleResponseDto> articles;

    public ShoppingCartResponseDto() {
    }

    public List<ShoppingCartArticleResponseDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ShoppingCartArticleResponseDto> articles) {
        this.articles = articles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
