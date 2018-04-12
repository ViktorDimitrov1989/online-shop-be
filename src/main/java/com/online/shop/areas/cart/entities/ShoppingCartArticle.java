package com.online.shop.areas.cart.entities;


import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.entities.Color;
import com.online.shop.areas.articles.entities.Size;

import javax.persistence.*;

@Entity
@Table(name = "shopping_cart_articles")
public class ShoppingCartArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne(optional = false)
    @JoinColumn(name="size_id")
    private Size chosenSize;

    @ManyToOne(optional = false)
    @JoinColumn(name="color_id")
    private Color chosenColor;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    public ShoppingCartArticle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Size getChosenSize() {
        return chosenSize;
    }

    public void setChosenSize(Size chosenSize) {
        this.chosenSize = chosenSize;
    }

    public Color getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(Color chosenColor) {
        this.chosenColor = chosenColor;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
