package com.online.shop.articles;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.entities.*;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.enums.Status;
import com.online.shop.areas.articles.models.binding.CreateArticleBindingModel;
import com.online.shop.areas.articles.repositories.ArticleRepository;
import com.online.shop.areas.articles.serviceImpl.*;
import com.online.shop.utils.PictureUploader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class ArticleServiceTests {

    /*@Autowired
    private TestEntityManager testEntityManager;*/

    @Mock
    private ArticleStatusServiceImpl articleStatusService;

    @Mock
    private BrandServiceImpl brandService;

    @Mock
    private ColorServiceImpl colorService;

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private SizeServiceImpl sizeService;

    @Mock
    private PictureUploader pictureUploader;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private ModelMapper realModelMapper;

    private CreateArticleBindingModel testArticle;

    final String foodName = "testimage";
    final String foodNameExt = "jpg";
    private MultipartFile getImage() throws IOException {
        FileInputStream inputFile = new FileInputStream( "src/test/resources/testimage.jpg");
        return new MockMultipartFile("file", foodName+"."+foodNameExt, "multipart/form-data", inputFile);

    }

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
        MockitoAnnotations.initMocks(this);

        this.testArticle = new CreateArticleBindingModel();
        this.testArticle.setName("Панталони");
        this.testArticle.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been");
        this.testArticle.setBrandName("Nike");
        this.testArticle.setCategory(1L);
        this.testArticle.setColors(new HashSet<String>(){{add("Червен");}});
        this.testArticle.setSizes(new HashSet<String>(){{add("M");}});
        this.testArticle.setDiscount(5);
        this.testArticle.setExpireDate(new Date());
        this.testArticle.setIsAvailable(true);
        this.testArticle.setPrice(new BigDecimal("10.00"));
        this.testArticle.setStatus(Status.PROMO);
        this.testArticle.setExpireDate(this.getCorrectExpireDate());


        Brand brandToReturn = new Brand();
        brandToReturn.setId(1L);
        brandToReturn.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been");
        brandToReturn.setName("Nike");

        doReturn(brandToReturn).when(this.brandService).findBrandByName(this.testArticle.getBrandName());

        String pictureUrl = "pictureUrl";

            /*when(this.pictureUploader.uploadPic(getImage()))
                    .thenReturn(pictureUrl);*/
            doReturn(pictureUrl).when(this.pictureUploader).uploadPic(any());


        Size sizeToReturn = new Size();
        sizeToReturn.setId(1L);
        sizeToReturn.setName("M");
        doReturn(new HashSet<>(){{add(sizeToReturn);}}).when(this.sizeService).findAllSizesIn(this.testArticle.getSizes());

        Color colorToReturn = new Color();
        colorToReturn.setId(1L);
        colorToReturn.setName("Червен");

        doReturn(new HashSet<>(){{add(colorToReturn);}}).when(this.colorService).findAllColorsIn(this.testArticle.getColors());


        ArticleStatus articleStatusToReturn = new ArticleStatus();
        articleStatusToReturn.setAvailable(this.testArticle.isAvailable());
        articleStatusToReturn.setStatus(this.testArticle.getStatus());
        articleStatusToReturn.setExpireDate(this.testArticle.getExpireDate());
        articleStatusToReturn.setDiscount(this.testArticle.getDiscount());

        Category categoryToReturn = new Category();
        categoryToReturn.setGender(Gender.BOYS);
        categoryToReturn.setId(1L);
        categoryToReturn.setMinAge(2);
        categoryToReturn.setMaxAge(5);
        categoryToReturn.setName("Панталони");
        categoryToReturn.setSeason(Season.SPRING_SUMMER);

        doReturn(categoryToReturn).when(this.categoryService).findCategoryById(1L);


        Article article = new Article();
        article.setStatus(articleStatusToReturn);
        article.setDescription(this.testArticle.getDescription());
        article.setColors(new HashSet<>(){{add(colorToReturn);}});
        article.setSizes(new HashSet<>(){{add(sizeToReturn);}});
        article.setPrice(this.testArticle.getPrice());
        article.setName(this.testArticle.getName());
        article.setPhoto(pictureUrl);
        article.setBrand(brandToReturn);
        article.setCategory(categoryToReturn);
        article.setId(1L);
        article.setArticles(new HashSet<>());


        Article articleToCreate = new Article();
        articleToCreate.setName(this.testArticle.getName());
        articleToCreate.setPhoto(pictureUrl);
        articleToCreate.setDescription(this.testArticle.getDescription());
        articleToCreate.setPrice(this.testArticle.getPrice());
        doReturn(articleToCreate).when(this.modelMapper).map(this.testArticle, Article.class);

        ArticleStatus articleStatusToReturnFromModelMapper = new ArticleStatus();
        articleStatusToReturnFromModelMapper.setAvailable(this.testArticle.isAvailable());
        articleStatusToReturnFromModelMapper.setDiscount(this.testArticle.getDiscount());
        articleStatusToReturnFromModelMapper.setExpireDate(this.testArticle.getExpireDate());
        articleStatusToReturnFromModelMapper.setStatus(this.testArticle.getStatus());
        doReturn(articleStatusToReturnFromModelMapper).when(this.modelMapper).map(this.testArticle, ArticleStatus.class);

        articleStatusToReturnFromModelMapper.setId(1L);
        doReturn(articleStatusToReturnFromModelMapper).when(this.articleStatusService).createArticleStatus(articleStatusToReturnFromModelMapper);

        articleToCreate.setSizes(this.sizeService.findAllSizesIn(this.testArticle.getSizes()));
        articleToCreate.setColors(this.colorService.findAllColorsIn(this.testArticle.getColors()));
        articleToCreate.setStatus(articleStatusToReturnFromModelMapper);
        articleToCreate.setCategory(this.categoryService.findCategoryById(1L));
        articleToCreate.setBrand(brandToReturn);
        articleToCreate.setId(1L);

        doReturn(articleToCreate).when(this.articleRepository).save(articleToCreate);


        ArticleResponseDto response = this.realModelMapper.map(articleToCreate, ArticleResponseDto.class);
        doReturn(response).when(this.modelMapper).map(articleToCreate, ArticleResponseDto.class);

        response.setSizes(articleToCreate.getSizes().stream().map(Size::getName).collect(Collectors.toSet()));
        response.setColors(articleToCreate.getColors().stream().map(Color::getName).collect(Collectors.toSet()));

        when(this.articleService.createArticle(this.testArticle, any())).thenReturn(response);
    }


    @Test
    public void testCreateArticle_WitValidArticle_ShouldNotReturnNull() throws IOException {
        ArticleResponseDto articleResponse = this.articleService.createArticle(this.testArticle, getImage());

        Assert.assertNotNull("Article response object is null after creation.", articleResponse);
    }



}
