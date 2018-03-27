package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.articleStatus.ArticleStatusResponseDto;
import com.online.shop.areas.articles.entities.ArticleStatus;

public interface ArticleStatusService {

    ArticleStatusResponseDto createArticleStatus(ArticleStatus articleStatus);

}
