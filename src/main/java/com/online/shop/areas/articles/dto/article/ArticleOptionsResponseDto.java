package com.online.shop.areas.articles.dto.article;

import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.dto.colors.ColorResponseDto;
import com.online.shop.areas.articles.dto.sizes.SizeResponseDto;

import java.util.List;

public class ArticleOptionsResponseDto {

    private List<BrandResponseDto> brands;

    private List<CategoryResponseDto> categories;

    private List<SizeResponseDto> sizes;

    private List<ColorResponseDto> colors;

    public ArticleOptionsResponseDto() {
    }

    public ArticleOptionsResponseDto(List<BrandResponseDto> brands, List<CategoryResponseDto> categories, List<SizeResponseDto> sizes, List<ColorResponseDto> colors) {
        this.brands = brands;
        this.categories = categories;
        this.sizes = sizes;
        this.colors = colors;
    }

    public List<BrandResponseDto> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandResponseDto> brands) {
        this.brands = brands;
    }

    public List<CategoryResponseDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponseDto> categories) {
        this.categories = categories;
    }

    public List<SizeResponseDto> getSizes() {
        return sizes;
    }

    public void setSizes(List<SizeResponseDto> sizes) {
        this.sizes = sizes;
    }

    public List<ColorResponseDto> getColors() {
        return colors;
    }

    public void setColors(List<ColorResponseDto> colors) {
        this.colors = colors;
    }
}
