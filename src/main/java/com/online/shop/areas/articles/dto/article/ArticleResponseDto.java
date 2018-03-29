package com.online.shop.areas.articles.dto.article;

import com.online.shop.areas.articles.dto.articleStatus.ArticleStatusResponseDto;
import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.dto.category.CategoryResponseDto;

import java.math.BigDecimal;
import java.util.Set;

public class ArticleResponseDto {


    private Long id;

    private String name;

    private String description;

    private String photo;

    private BrandResponseDto brand;

    private ArticleStatusResponseDto status;

    private BigDecimal price;

    private Set<String> sizes;

    private Set<String> colors;

    private CategoryResponseDto category;

    public ArticleResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BrandResponseDto getBrand() {
        return brand;
    }

    public void setBrand(BrandResponseDto brand) {
        this.brand = brand;
    }

    public ArticleStatusResponseDto getStatus() {
        return status;
    }

    public void setStatus(ArticleStatusResponseDto status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<String> getSizes() {
        return sizes;
    }

    public void setSizes(Set<String> sizes) {
        this.sizes = sizes;
    }

    public Set<String> getColors() {
        return colors;
    }

    public void setColors(Set<String> colors) {
        this.colors = colors;
    }

    public CategoryResponseDto getCategory() {
        return category;
    }

    public void setCategory(CategoryResponseDto category) {
        this.category = category;
    }
}
