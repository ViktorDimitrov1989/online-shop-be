package com.online.shop.areas.articles.controllers;

import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.models.binding.CreateBrandBindingModel;
import com.online.shop.areas.articles.services.BrandService;
import com.online.shop.areas.users.dto.user.UserResponseDto;
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
@RequestMapping("/admin/brand")
public class AdminBrandController {

    private BrandService brandService;

    @Autowired
    public AdminBrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<Response> register(@Valid @RequestBody CreateBrandBindingModel createBrandBindingModel,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestException(ErrorMessageHelper.formatMessage(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }

        BrandResponseDto createdBrand = this.brandService.createBrand(createBrandBindingModel);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.CREATE_BRAND_SUCCESS, createdBrand), HttpStatus.CREATED);
    }



}
