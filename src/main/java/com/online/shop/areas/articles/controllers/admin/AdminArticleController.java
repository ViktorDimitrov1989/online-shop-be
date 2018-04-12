package com.online.shop.areas.articles.controllers.admin;

import com.online.shop.areas.articles.common.ArticleConverter;
import com.online.shop.areas.articles.dto.article.ArticleOptionsResponseDto;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.entities.Color;
import com.online.shop.areas.articles.entities.Size;
import com.online.shop.areas.articles.models.binding.*;
import com.online.shop.areas.articles.services.ArticleService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ErrorMessageHelper;
import com.online.shop.response.Response;
import com.online.shop.response.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    private ArticleService articleService;

    private ArticleConverter articleConverter;

    @Autowired
    public AdminArticleController(
            ArticleService articleService,
            ArticleConverter articleConverter) {
        this.articleService = articleService;
        this.articleConverter = articleConverter;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<Response> createArticle(@Valid @RequestPart(name = "article", required = true) CreateArticleBindingModel article,
                                             @RequestPart(name = "photo", required = true) MultipartFile photo,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestException(ErrorMessageHelper.formatMessage(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }

        ArticleResponseDto createdArticle = this.articleService.createArticle(article, photo);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.CREATE_ARTICLE_SUCCESS, createdArticle), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public ResponseEntity<Response> createArticle(@Valid @RequestPart(name = "article", required = true) EditArticleBindingModel article,
                                                  @RequestPart(name = "photo", required = false) MultipartFile photo,
                                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestException(ErrorMessageHelper.formatMessage(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }

        ArticleResponseDto edited = this.articleService.editArticle(article, photo);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.EDIT_ARTICLE_SUCCESS, edited), HttpStatus.OK);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<Response> createArticle(@PathVariable("id") Long id){

        ArticleResponseDto deleted = this.articleService.deleteArticleById(id);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.DELETE_ARTICLE_SUCCESS, deleted), HttpStatus.OK);
    }

}
