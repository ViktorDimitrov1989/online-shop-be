package com.online.shop.areas.articles.controllers;

import com.online.shop.areas.articles.dto.article.ArticleOptionsResponseDto;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.services.ArticleService;
import com.online.shop.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/unAuth")
public class UnAuthArticleController {

    private final ArticleService articleService;

    @Autowired
    public UnAuthArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(path = "/adverts", method = RequestMethod.GET)
    public ResponseEntity<Response> getArticleOptions(){

        List<ArticleResponseDto> articles = this.articleService.getAdverts();

        return new ResponseEntity<>(new Response("", articles), HttpStatus.OK);
    }


}
