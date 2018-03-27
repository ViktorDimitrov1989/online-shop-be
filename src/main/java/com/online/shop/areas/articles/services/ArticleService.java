package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.models.binding.CreateArticleBindingModel;

public interface ArticleService {

    ArticleResponseDto createArticle(CreateArticleBindingModel createArticleBindingModel);

}
