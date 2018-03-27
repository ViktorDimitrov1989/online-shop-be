package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.models.binding.CreateCategoryBindingModel;

public interface CategoryService {

    CategoryResponseDto createCategory(CreateCategoryBindingModel createCategoryBindingModel);

}
