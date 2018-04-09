package com.online.shop.areas.articles.controllers.admin;

import com.online.shop.areas.articles.dto.articleStatus.ArticleStatusResponseDto;
import com.online.shop.areas.articles.models.binding.EditArticleStatusBindingModel;
import com.online.shop.areas.articles.services.ArticleStatusService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ErrorMessageHelper;
import com.online.shop.response.Response;
import com.online.shop.response.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/admin/articleStatus")
public class AdminArticleStatusController {

    private ArticleStatusService articleStatusService;

    @Autowired
    public AdminArticleStatusController(ArticleStatusService articleStatusService) {
        this.articleStatusService = articleStatusService;
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public ResponseEntity<Response> editArticleStatus(
            @Valid @RequestBody EditArticleStatusBindingModel editArticleStatusBindingModel,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new RequestException(ErrorMessageHelper.formatMessage(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }

        ArticleStatusResponseDto resp = this.articleStatusService.editArticleStatus(editArticleStatusBindingModel);

        return new ResponseEntity<>(new Response(ResponseMessageConstants.EDIT_ARTICLE_STATUS_SUCCESS, resp), HttpStatus.CREATED);
    }


}
