package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.entities.Brand;
import com.online.shop.areas.articles.models.binding.CreateBrandBindingModel;

public interface BrandService {

    BrandResponseDto createBrand(CreateBrandBindingModel createBrandBindingModel);

    Brand findBrandByName(String name);
}
