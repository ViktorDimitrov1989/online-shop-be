package com.online.shop.areas.articles.services;

import com.online.shop.areas.articles.dto.articleStatus.ArticleStatusResponseDto;
import com.online.shop.areas.articles.entities.ArticleStatus;

import java.util.List;

public interface ArticleStatusService {

    ArticleStatus createArticleStatus(ArticleStatus articleStatus);

    List<ArticleStatusResponseDto> findArticleStatuses();
}
