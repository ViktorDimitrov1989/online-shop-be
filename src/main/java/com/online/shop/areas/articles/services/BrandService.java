package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.entities.Brand;
import com.online.shop.areas.articles.models.binding.CreateBrandBindingModel;

import java.util.List;
import java.util.Set;

public interface BrandService {

    BrandResponseDto createBrand(CreateBrandBindingModel createBrandBindingModel);

    List<BrandResponseDto> findAllBrands();

    Brand findBrandByName(String name);
}
