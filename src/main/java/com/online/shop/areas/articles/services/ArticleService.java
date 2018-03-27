package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.models.binding.CreateArticleBindingModel;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {

    ArticleResponseDto createArticle(CreateArticleBindingModel createArticleBindingModel, MultipartFile photo);

}
