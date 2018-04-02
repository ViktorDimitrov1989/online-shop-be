package com.online.shop.areas.articles.serviceImpl;
import com.online.shop.areas.articles.dto.articleStatus.ArticleStatusResponseDto;
import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.entities.ArticleStatus;
import com.online.shop.areas.articles.repositories.ArticleStatusRepository;
import com.online.shop.areas.articles.services.ArticleStatusService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ArticleStatusServiceImpl implements ArticleStatusService {

    private ArticleStatusRepository articleStatusRepository;

    private ModelMapper modelMapper;

    public ArticleStatusServiceImpl(ArticleStatusRepository articleStatusRepository, ModelMapper modelMapper) {
        this.articleStatusRepository = articleStatusRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ArticleStatus createArticleStatus(ArticleStatus articleStatus) {
        ArticleStatus createdStatus = this.articleStatusRepository.save(articleStatus);

        return createdStatus;
    }

    @Override
    public List<ArticleStatusResponseDto> findArticleStatuses() {
        List<ArticleStatus> statuses = this.articleStatusRepository.findAll();

        Type targetListType = new TypeToken<List<ArticleStatusResponseDto>>() {}.getType();

        return this.modelMapper.map(statuses, targetListType);
    }
}
