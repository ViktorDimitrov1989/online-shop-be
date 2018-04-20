package com.online.shop.articleStatuses;


import com.online.shop.areas.articles.dto.articleStatus.ArticleStatusResponseDto;
import com.online.shop.areas.articles.entities.ArticleStatus;
import com.online.shop.areas.articles.enums.Status;
import com.online.shop.areas.articles.models.binding.EditArticleStatusBindingModel;
import com.online.shop.areas.articles.repositories.ArticleStatusRepository;
import com.online.shop.areas.articles.serviceImpl.ArticleStatusServiceImpl;
import com.online.shop.exception.RequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class ArticleStatusServiceTests {

    @Mock
    private ArticleStatusRepository articleStatusRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArticleStatusServiceImpl articleStatusService;

    private ModelMapper realModelMapper;

    private ArticleStatus testArticleStatus;

    private EditArticleStatusBindingModel editArticleStatus;

    private List<ArticleStatus> articleStatusesToReturnFromTheRepository;

    private Date getCorrectExpireDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "07/06/2025";

        try {

            Date date = formatter.parse(dateInString);
            return date;


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Before
    public void init(){
        this.realModelMapper = new ModelMapper();

        this.testArticleStatus = new ArticleStatus();
        this.testArticleStatus.setId(1L);
        this.testArticleStatus.setStatus(Status.REGULAR);
        this.testArticleStatus.setExpireDate(getCorrectExpireDate());
        this.testArticleStatus.setDiscount(5);
        this.testArticleStatus.setAvailable(true);

        this.articleStatusesToReturnFromTheRepository = new ArrayList<>(){{add(testArticleStatus);}};

        doReturn(this.testArticleStatus).when(this.articleStatusRepository).save(this.testArticleStatus);
        doReturn(this.articleStatusesToReturnFromTheRepository).when(this.articleStatusRepository).findAll();

        this.editArticleStatus = new EditArticleStatusBindingModel();
        this.editArticleStatus.setAvailable(false);
        this.editArticleStatus.setDiscount(3);
        this.editArticleStatus.setExpireDate(null);
        this.editArticleStatus.setId(1L);
        this.editArticleStatus.setStatus(Status.PROMO);

        doReturn(this.testArticleStatus).when(this.articleStatusRepository).findOneById(1L);
    }

    @Test
    public void testCreateArticleStatus_WithCorrectData_ShouldNotReturnNull(){
        assertNotNull("Returned article status on save is null.", this.articleStatusService.createArticleStatus(this.testArticleStatus));
    }

    @Test
    public void testFindArticleStatuses_ShouldNotReturnNull(){
        Type targetListType = new TypeToken<List<ArticleStatusResponseDto>>() {}.getType();

        doReturn(this.realModelMapper.map(this.articleStatusesToReturnFromTheRepository, targetListType))
                .when(this.modelMapper).map(this.articleStatusesToReturnFromTheRepository, targetListType);

        assertNotNull("Returned article statuses are null.", this.articleStatusService.findArticleStatuses());
    }

    @Test
    public void testFindArticleStatuses_ShouldNotBeEmpty(){
        Type targetListType = new TypeToken<List<ArticleStatusResponseDto>>() {}.getType();

        doReturn(this.realModelMapper.map(this.articleStatusesToReturnFromTheRepository, targetListType))
                .when(this.modelMapper).map(this.articleStatusesToReturnFromTheRepository, targetListType);

        assertTrue("Returned article statuses collection is empty.", !this.articleStatusService.findArticleStatuses().isEmpty());
    }

    @Test
    public void testFindArticleStatuses_ShouldBeEmpty(){
        this.articleStatusesToReturnFromTheRepository.clear();
        doReturn(this.articleStatusesToReturnFromTheRepository).when(this.articleStatusRepository).findAll();

        Type targetListType = new TypeToken<List<ArticleStatusResponseDto>>() {}.getType();

        doReturn(this.realModelMapper.map(this.articleStatusesToReturnFromTheRepository, targetListType))
                .when(this.modelMapper).map(this.articleStatusesToReturnFromTheRepository, targetListType);

        List<ArticleStatusResponseDto> response = this.articleStatusService.findArticleStatuses();

        assertTrue("Returned article statuses collection is not empty.", response.isEmpty());
    }

    @Test
    public void testEditArticleStatuses_WithCorrectData_ShouldNotBeNull(){
        this.testArticleStatus.setAvailable(this.editArticleStatus.isAvailable());
        this.testArticleStatus.setDiscount(this.editArticleStatus.getDiscount());
        this.testArticleStatus.setExpireDate(this.editArticleStatus.getExpireDate());
        this.testArticleStatus.setStatus(this.editArticleStatus.getStatus());

        doReturn(this.realModelMapper
                .map(this.testArticleStatus, ArticleStatusResponseDto.class))
                .when(this.modelMapper)
                .map(this.testArticleStatus, ArticleStatusResponseDto.class);

        ArticleStatusResponseDto resp = this.articleStatusService.editArticleStatus(this.editArticleStatus);

        assertNotNull("Article status is null.", resp);
    }

    @Test
    public void testEditArticleStatuses_WithCorrectData_AvailableShouldBeCorrect(){
        this.testArticleStatus.setAvailable(this.editArticleStatus.isAvailable());
        this.testArticleStatus.setDiscount(this.editArticleStatus.getDiscount());
        this.testArticleStatus.setExpireDate(this.editArticleStatus.getExpireDate());
        this.testArticleStatus.setStatus(this.editArticleStatus.getStatus());

        ArticleStatusResponseDto editResponse = this.realModelMapper
                .map(this.testArticleStatus, ArticleStatusResponseDto.class);

        doReturn(editResponse)
                .when(this.modelMapper)
                .map(this.testArticleStatus, ArticleStatusResponseDto.class);

        ArticleStatusResponseDto resp = this.articleStatusService.editArticleStatus(this.editArticleStatus);

        assertTrue("Article status isAvailable property was not edit correctly.", resp.isAvailable() == editResponse.isAvailable());
    }

    @Test(expected = RequestException.class)
    public void testEditArticleStatuses_WithInvalidArticleStatusId_ShouldThroExpetion(){
        this.editArticleStatus.setId(2L);

        this.articleStatusService.editArticleStatus(this.editArticleStatus);
    }



}
