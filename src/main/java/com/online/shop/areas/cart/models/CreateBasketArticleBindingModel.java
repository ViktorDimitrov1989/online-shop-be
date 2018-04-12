package com.online.shop.areas.cart.models;

import javax.validation.constraints.NotNull;

public class CreateBasketArticleBindingModel {

    @NotNull(message = "Shopping cart id is missing.")
    private Long shoppingCartId;
    @NotNull(message = "Article id is missing.")
    private Long articleId;
    @NotNull(message = "Chosen size is missing.")
    private String size;
    @NotNull(message = "Chosen color is missing.")
    private String color;

    private int quantity;

    public CreateBasketArticleBindingModel() {
        this.quantity = 1;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
}
