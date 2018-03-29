package com.online.shop.areas.articles.controllers.admin;
import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.models.binding.CreateCategoryBindingModel;
import com.online.shop.areas.articles.services.CategoryService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ErrorMessageHelper;
import com.online.shop.response.Response;
import com.online.shop.response.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<Response> register(@Valid @RequestBody CreateCategoryBindingModel createCategoryBindingModel,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestException(ErrorMessageHelper.formatMessage(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }

        CategoryResponseDto createdCategory = this.categoryService.createCategory(createCategoryBindingModel);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.CREATE_CATEGORY_SUCCESS, createdCategory), HttpStatus.CREATED);
    }



}
