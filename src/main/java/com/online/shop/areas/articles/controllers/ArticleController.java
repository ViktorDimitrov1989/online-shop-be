package com.online.shop.areas.articles.controllers;

import com.online.shop.areas.articles.dto.article.ArticleOptionsResponseDto;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.models.binding.FilterArticlesBindingModel;
import com.online.shop.areas.articles.services.ArticleService;
import com.online.shop.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(path = "/filter", params = { "page", "size" }, method = RequestMethod.POST)
    public ResponseEntity<Response> filterArticles(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestBody FilterArticlesBindingModel filterArticlesBindingModel){

        Page<ArticleResponseDto> articlesResponse = this.articleService.findFilteredArticles(page, size, filterArticlesBindingModel);

        return new ResponseEntity<>(new Response("", articlesResponse), HttpStatus.OK);
    }

    @RequestMapping(path = "/options", method = RequestMethod.GET)
    public ResponseEntity<Response> getArticleOptions(){

        ArticleOptionsResponseDto articleOptions = this.articleService.getArticleOptions();

        return new ResponseEntity<>(new Response("", articleOptions), HttpStatus.OK);
    }



}
