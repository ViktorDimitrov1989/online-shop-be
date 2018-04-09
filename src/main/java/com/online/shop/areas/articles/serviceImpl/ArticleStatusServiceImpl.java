package com.online.shop.areas.articles.serviceImpl;
import com.online.shop.areas.articles.dto.articleStatus.ArticleStatusResponseDto;
import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.entities.ArticleStatus;
import com.online.shop.areas.articles.models.binding.EditArticleStatusBindingModel;
import com.online.shop.areas.articles.repositories.ArticleStatusRepository;
import com.online.shop.areas.articles.services.ArticleStatusService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
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

    @Override
    public ArticleStatusResponseDto editArticleStatus(EditArticleStatusBindingModel editArticleStatusBindingModel) {

        ArticleStatus status = this.articleStatusRepository.findOneById(editArticleStatusBindingModel.getId());

        if(status == null){
            throw new RequestException(ResponseMessageConstants.INVALID_STATUS_ID, HttpStatus.BAD_REQUEST);
        }

        status.setAvailable(editArticleStatusBindingModel.isAvailable());
        status.setDiscount(editArticleStatusBindingModel.getDiscount());
        status.setExpireDate(editArticleStatusBindingModel.getExpireDate());
        status.setStatus(editArticleStatusBindingModel.getStatus());

        ArticleStatus savedStatus = this.articleStatusRepository.save(status);

        return this.modelMapper.map(savedStatus, ArticleStatusResponseDto.class);
    }
}
