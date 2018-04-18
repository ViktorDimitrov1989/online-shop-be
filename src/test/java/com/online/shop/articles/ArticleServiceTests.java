package com.online.shop.articles;


import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.entities.*;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.enums.Status;
import com.online.shop.areas.articles.models.binding.CreateArticleBindingModel;
import com.online.shop.areas.articles.repositories.ArticleRepository;
import com.online.shop.areas.articles.serviceImpl.*;
import com.online.shop.areas.articles.services.*;
import com.online.shop.utils.PictureUploader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
public class ArticleServiceTests {

    @Autowired
    private TestEntityManager testEntityManager;

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

        when(this.brandService.findBrandByName(this.testArticle.getBrandName()))
                .thenReturn(brandToReturn);

        String pictureUrl = "pictureUrl";
        try {
            when(this.pictureUploader.uploadPic(getImage()))
                    .thenReturn(pictureUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Size sizeToReturn = new Size();
        sizeToReturn.setId(1L);
        sizeToReturn.setName("M");
        when(this.sizeService.findAllSizesIn(this.testArticle.getSizes()))
            .thenReturn(new HashSet<>(){{add(sizeToReturn);}});

        Color colorToReturn = new Color();
        colorToReturn.setId(1L);
        colorToReturn.setName("Червен");

        when(this.colorService.findAllColorsIn(this.testArticle.getColors()))
                .thenReturn(new HashSet<>(){{add(colorToReturn);}});


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

        when(this.categoryService.findCategoryById(1L))
                .thenReturn(categoryToReturn);


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

        when(this.articleRepository.save(article))
                .thenAnswer(a -> a.getArgument(0));

    }


    @Test
    public void testCreateArticle_WitValidArticle_ShouldNotReturnNull() throws IOException {
        ArticleResponseDto articleResponse = this.articleService.createArticle(this.testArticle, getImage());

        Assert.assertNotNull("Article response object is null after creation.", articleResponse);
    }



}
