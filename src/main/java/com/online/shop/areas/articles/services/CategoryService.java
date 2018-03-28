package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.entities.Category;
import com.online.shop.areas.articles.models.binding.CreateCategoryBindingModel;

import java.util.List;

public interface CategoryService {

    Category findCategoryById(Long id);

    CategoryResponseDto createCategory(CreateCategoryBindingModel createCategoryBindingModel);

    List<CategoryResponseDto> findAllCategories();
}
