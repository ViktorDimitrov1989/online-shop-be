package com.online.shop.areas.cart.dto.cartArticle;

import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.dto.colors.ColorResponseDto;
import com.online.shop.areas.articles.dto.sizes.SizeResponseDto;

public class ShoppingCartArticleResponseDto {

    private Long id;

    private ArticleResponseDto article;

    private SizeResponseDto chosenSize;

    private ColorResponseDto chosenColor;

    public ShoppingCartArticleResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArticleResponseDto getArticle() {
        return article;
    }

    public void setArticle(ArticleResponseDto article) {
        this.article = article;
    }

    public SizeResponseDto getChosenSize() {
        return chosenSize;
    }

    public void setChosenSize(SizeResponseDto chosenSize) {
        this.chosenSize = chosenSize;
    }

    public ColorResponseDto getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(ColorResponseDto chosenColor) {
        this.chosenColor = chosenColor;
    }
}
