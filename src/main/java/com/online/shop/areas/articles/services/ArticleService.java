package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.article.ArticleOptionsResponseDto;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.models.binding.CreateArticleBindingModel;
import com.online.shop.areas.articles.models.binding.EditArticleBindingModel;
import com.online.shop.areas.articles.models.binding.EditArticleStatusBindingModel;
import com.online.shop.areas.articles.models.binding.FilterArticlesBindingModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public interface ArticleService {

    Article getArticleById(Long id);

    ArticleResponseDto createArticle(CreateArticleBindingModel createArticleBindingModel, MultipartFile photo);

    ArticleOptionsResponseDto getArticleOptions();

    Page<ArticleResponseDto> findAllArticles(int page, int size);

    Page<ArticleResponseDto> findFilteredArticles(int page, int size, FilterArticlesBindingModel filterArticlesBindingModel);

    ArticleResponseDto editArticle(EditArticleBindingModel article, MultipartFile photo);

    ArticleResponseDto deleteArticleById(Long id);
}
