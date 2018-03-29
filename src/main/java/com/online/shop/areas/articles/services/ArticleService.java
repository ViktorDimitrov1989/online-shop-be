package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.article.ArticleOptionsResponseDto;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.models.binding.CreateArticleBindingModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {

    ArticleResponseDto createArticle(CreateArticleBindingModel createArticleBindingModel, MultipartFile photo);

    ArticleOptionsResponseDto getArticleOptions();

    Page<ArticleResponseDto> findAllArticles(int page, int size);
}
