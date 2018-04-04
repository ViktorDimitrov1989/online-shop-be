package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.entities.Category;
import com.online.shop.areas.articles.models.binding.CreateCategoryBindingModel;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category findCategoryById(Long id);

    Set<Category> findCategoriesByIds(Collection<Long> ids);

    Set<Category> findAllRawCategories();

    CategoryResponseDto createCategory(CreateCategoryBindingModel createCategoryBindingModel);

    List<CategoryResponseDto> findAllCategories();
}
