package com.online.shop.areas.articles.controllers;

import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
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

    @RequestMapping(path = "/all", params = { "page", "size" }, method = RequestMethod.GET)
    public ResponseEntity<Response> getArticles(@RequestParam("page") int page, @RequestParam("size") int size){

        Page<ArticleResponseDto> articlesResponse = this.articleService.findAllArticles(page, size);

        return new ResponseEntity<>(new Response("", articlesResponse), HttpStatus.OK);
    }



}
