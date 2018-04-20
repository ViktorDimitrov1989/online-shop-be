package com.online.shop.articles;

import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.repositories.ArticleRepository;
import com.online.shop.areas.articles.serviceImpl.*;
import com.online.shop.utils.PictureUploader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class AdvertsArticleServiceTests {

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

    private Article advert;

    @Before
    public void init(){
        this.advert = new Article();
        this.advert.setColors(new HashSet<>());
        this.advert.setSizes(new HashSet<>());

    }

    @Test
    public void testGetAdverts_ShouldNotReturnNull(){
        List<Article> adverts = new ArrayList<>(){{add(advert);}};
        doReturn(adverts).when(this.articleRepository).findLastAddedArticles();

        assertNotNull("Adverts are null.", this.articleService.getAdverts());
    }

    @Test
    public void testGetAdverts_ShouldNotBeEmpty(){
        List<Article> adverts = new ArrayList<>(){{add(advert);}};
        doReturn(adverts).when(this.articleRepository).findLastAddedArticles();
        doReturn(new ArticleResponseDto()).when(this.modelMapper).map(adverts.get(0), ArticleResponseDto.class);

        assertTrue("Adverts are empty.", !this.articleService.getAdverts().isEmpty());
    }

    @Test
    public void testGetAdverts_ShouldBeEmpty(){
        List<Article> adverts = new ArrayList<>();
        doReturn(adverts).when(this.articleRepository).findLastAddedArticles();

        assertTrue("Adverts are not empty.", this.articleService.getAdverts().isEmpty());
    }



}
