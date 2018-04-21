package com.online.shop.areas.articles.serviceImpl;
import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.entities.Category;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.models.binding.CreateCategoryBindingModel;
import com.online.shop.areas.articles.repositories.CategoryRepository;
import com.online.shop.areas.articles.services.CategoryService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Category findCategoryById(Long id) {
        Category category = this.categoryRepository.findOneById(id);

        if(category == null){
            throw new RequestException(ResponseMessageConstants.INVALID_CATEGORY_ID,HttpStatus.BAD_REQUEST);
        }

        return category;
    }

    @Override
    public Set<Category> findCategoriesByIds(Collection<Long> ids) {
        return this.categoryRepository.findAllByIdIn(ids);
    }

    @Override
    public Set<Category> findAllRawCategories() {
        return new HashSet<>(this.categoryRepository.findAll());
    }

    @Override
    public CategoryResponseDto createCategory(CreateCategoryBindingModel createCategoryBindingModel) {

        if(!Season.isPresent(createCategoryBindingModel.getSeason())){
            throw  new RequestException(ResponseMessageConstants.INVALID_SEASON, HttpStatus.CONFLICT);
        }

        if(!Gender.isPresent(createCategoryBindingModel.getGender())){
            throw  new RequestException(ResponseMessageConstants.INVALID_GENDER, HttpStatus.CONFLICT);
        }

        if(this.categoryRepository.findOneByNameAndGenderAndSeason(createCategoryBindingModel.getName(),
                Gender.valueOf(createCategoryBindingModel.getGender()),
                        Season.valueOf(createCategoryBindingModel.getSeason())) != null){
            throw  new RequestException(ResponseMessageConstants.EXISTING_CATEGORY, HttpStatus.CONFLICT);
        }



        Category category = this.modelMapper.map(createCategoryBindingModel, Category.class);

        Category createdCategory = this.categoryRepository.save(category);

        CategoryResponseDto createdCategoryResponse = this.modelMapper.map(createdCategory, CategoryResponseDto.class);

        return createdCategoryResponse;
    }

    @Override
    public List<CategoryResponseDto> findAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();

        Type targetListType = new TypeToken<List<CategoryResponseDto>>() {}.getType();

        return this.modelMapper.map(categories, targetListType);
    }
}
