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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public CategoryResponseDto createCategory(CreateCategoryBindingModel createCategoryBindingModel) {

        if(this.categoryRepository.findOneByName(createCategoryBindingModel.getName()) != null){
            throw  new RequestException(ResponseMessageConstants.EXISTING_CATEGORY, HttpStatus.CONFLICT);
        }

        if(!Season.isPresent(createCategoryBindingModel.getSeason())){
            throw  new RequestException(ResponseMessageConstants.INVALID_SEASON, HttpStatus.CONFLICT);
        }

        if(!Gender.isPresent(createCategoryBindingModel.getGender())){
            throw  new RequestException(ResponseMessageConstants.INVALID_GENDER, HttpStatus.CONFLICT);
        }

        Category category = this.modelMapper.map(createCategoryBindingModel, Category.class);

        Category createdCategory = this.categoryRepository.save(category);

        CategoryResponseDto createdCategoryResponse = this.modelMapper.map(createdCategory, CategoryResponseDto.class);

        return createdCategoryResponse;
    }
}
