package com.online.shop.areas.cart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.shop.areas.users.entities.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shoppingCart",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ShoppingCartArticle> articles;

    @JsonIgnore
    @OneToOne(mappedBy="shoppingCart")
    private User owner;

    public ShoppingCart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ShoppingCartArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<ShoppingCartArticle> articles) {
        this.articles = articles;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
