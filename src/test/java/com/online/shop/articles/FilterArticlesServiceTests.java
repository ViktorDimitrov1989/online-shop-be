package com.online.shop.articles;

import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.entities.*;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.enums.Status;
import com.online.shop.areas.articles.models.binding.FilterArticlesBindingModel;
import com.online.shop.areas.articles.repositories.ArticleRepository;
import com.online.shop.areas.articles.serviceImpl.*;
import com.online.shop.exception.RequestException;
import com.online.shop.utils.PictureUploader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class FilterArticlesServiceTests {

    private static final  int CORRECT_PAGE_CNT = 0;

    private static final  int CORRECT_PAGE_CONTENT_CNT = 1;

    private static final  int INCORRECT_PAGE_CNT = -1;

    private static final  int INCORRECT_PAGE_CONTENT_CNT = 0;

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


    private FilterArticlesBindingModel filterArticlesBindingModel;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        this.filterArticlesBindingModel = new FilterArticlesBindingModel();
        this.filterArticlesBindingModel.setSelectedStatuses(new ArrayList<>(){{add(Status.REGULAR);}});
        this.filterArticlesBindingModel.setChosenGender(Gender.BOYS);
        this.filterArticlesBindingModel.setChosenSeason(Season.SPRING_SUMMER);

        doReturn(new HashSet<Brand>(){{add(new Brand());}})
                .when(this.brandService)
                .findAllBrandsByNames(this.filterArticlesBindingModel.getSelectedBrands());

        doReturn(new HashSet<Color>(){{add(new Color());}})
                .when(this.colorService)
                .findAllColorsIn(this.filterArticlesBindingModel.getSelectedColors());

        doReturn(new HashSet(){{add(new Size());}})
                .when(this.sizeService)
                .findAllSizesIn(this.filterArticlesBindingModel.getSelectedSizes());

        doReturn(new HashSet(){{add(new Category());}})
                .when(this.categoryService)
                .findCategoriesByIds(this.filterArticlesBindingModel.getSelectedCategories());



        Article articleToMap = new Article();
        articleToMap.setId(1L);
        articleToMap.setCategory(new Category());
        articleToMap.setBrand(new Brand());
        articleToMap.setPhoto("photoURL");
        articleToMap.setName("Гащи");
        articleToMap.setPrice(new BigDecimal("5.98"));
        articleToMap.setSizes(new HashSet<>());
        articleToMap.setColors(new HashSet<>());
        articleToMap.setDescription("ut also the leap into electronic typesetting, remaining essentially unchanged. It was pop");
        articleToMap.setStatus(new ArticleStatus());

        doReturn(new ArticleResponseDto()).when(this.modelMapper).map(articleToMap, ArticleResponseDto.class);

        doReturn(new PageImpl<>(new ArrayList<Article>(){{add(articleToMap);}}))
                .when(this.articleRepository)
                .findAllDistinctBySizesInAndColorsInAndBrandInAndCategoryInAndCategorySeasonAndCategoryGenderAndStatusStatusIn(
                        any(),
                        any(),
                        any(),
                        any(),
                        any(),
                        any(),
                        any(),
                        any()
                );

    }


    @Test
    public void testFindFilteredArticles_WithCorrectFilterData_BrandsShouldNotBeNull(){
        assertNotNull("ArticleOptionsResponseDto is null when all filters are filled.", this.articleService.findFilteredArticles(CORRECT_PAGE_CNT,CORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel));
    }

   @Test
    public void testFindFilteredArticles_WithCorrectFilterData_PageShouldBeWithCorrectIndex(){
        Page<ArticleResponseDto> page =  this.articleService.findFilteredArticles(CORRECT_PAGE_CNT,CORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel);

        assertTrue("Incorrect page index.", page.getPageable().getPageNumber() == CORRECT_PAGE_CNT);
    }

    @Test
    public void testFindFilteredArticles_WithCorrectFilterData_PageSizeShouldBeWithCorrectSize(){
        Page<ArticleResponseDto> page =  this.articleService.findFilteredArticles(CORRECT_PAGE_CNT,CORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel);

        assertTrue("Incorrect page size.", page.getPageable().getPageSize() == CORRECT_PAGE_CONTENT_CNT);
    }

    @Test(expected = RequestException.class)
    public void testFindFilteredArticles_WithIncorrectPageCnt_ShouldThrowRequestException(){
        assertNotNull("Exception was not thrown on invalid page index.",
                this.articleService.findFilteredArticles(INCORRECT_PAGE_CNT, CORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel));
    }

    @Test(expected = RequestException.class)
    public void testFindFilteredArticles_WithIncorrectPageSizeCnt_ShouldThrowRequestException(){
        assertNotNull("Exception was not thrown on invalid page size.",
                this.articleService.findFilteredArticles(CORRECT_PAGE_CNT, INCORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel));
    }

    @Test
    public void testFindFilteredArticles_WitEmptySelectedBrandsFilters_BrandsShouldNotBeNull(){
        this.filterArticlesBindingModel.setSelectedCategories(new ArrayList<>(){{add(1L);}});
        this.filterArticlesBindingModel.setSelectedColors(new ArrayList<>(){{add("Green");}});
        this.filterArticlesBindingModel.setSelectedSizes(new ArrayList<>(){{add("M");}});
        this.filterArticlesBindingModel.setSelectedBrands(new ArrayList<>());

        doReturn(new HashSet<Color>(){{add(new Color());}})
                .when(this.colorService)
                .findAllColorsIn(this.filterArticlesBindingModel.getSelectedColors());

        doReturn(new HashSet(){{add(new Size());}})
                .when(this.sizeService)
                .findAllSizesIn(this.filterArticlesBindingModel.getSelectedSizes());

        doReturn(new HashSet(){{add(new Category());}})
                .when(this.categoryService)
                .findCategoriesByIds(this.filterArticlesBindingModel.getSelectedCategories());

        doReturn(new HashSet<Brand>(){{add(new Brand());}})
                .when(this.brandService)
                .findAllRawBrands();

        assertNotNull("ArticleOptionsResponseDto is null when no brand is selected.",
                this.articleService.findFilteredArticles(CORRECT_PAGE_CNT,CORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel));
    }

    @Test
    public void testFindFilteredArticles_WitEmptySelectedColors_BrandsShouldNotBeNull(){
        this.filterArticlesBindingModel.setSelectedCategories(new ArrayList<>(){{add(1L);}});
        this.filterArticlesBindingModel.setSelectedBrands(new ArrayList<>(){{add("Nike");}});
        this.filterArticlesBindingModel.setSelectedSizes(new ArrayList<>(){{add("M");}});
        this.filterArticlesBindingModel.setSelectedColors(new ArrayList<>());

        doReturn(new HashSet<Brand>(){{add(new Brand());}})
                .when(this.brandService)
                .findAllBrandsByNames(this.filterArticlesBindingModel.getSelectedBrands());

        doReturn(new HashSet(){{add(new Size());}})
                .when(this.sizeService)
                .findAllSizesIn(this.filterArticlesBindingModel.getSelectedSizes());

        doReturn(new HashSet(){{add(new Category());}})
                .when(this.categoryService)
                .findCategoriesByIds(this.filterArticlesBindingModel.getSelectedCategories());

        doReturn(new HashSet<Color>(){{add(new Color());}})
                .when(this.colorService)
                .findAllRawColors();

        assertNotNull("ArticleOptionsResponseDto is null when no color is selected.",
                this.articleService.findFilteredArticles(CORRECT_PAGE_CNT,CORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel));
    }

    @Test
    public void testFindFilteredArticles_WitEmptySelectedSizesFilters_BrandsShouldNotBeNull(){
        this.filterArticlesBindingModel.setSelectedCategories(new ArrayList<>(){{add(1L);}});
        this.filterArticlesBindingModel.setSelectedBrands(new ArrayList<>(){{add("Nike");}});
        this.filterArticlesBindingModel.setSelectedColors(new ArrayList<>(){{add("Green");}});
        this.filterArticlesBindingModel.setSelectedSizes(new ArrayList<>());

        doReturn(new HashSet<Brand>(){{add(new Brand());}})
                .when(this.brandService)
                .findAllBrandsByNames(this.filterArticlesBindingModel.getSelectedBrands());

        doReturn(new HashSet<Color>(){{add(new Color());}})
                .when(this.colorService)
                .findAllColorsIn(this.filterArticlesBindingModel.getSelectedColors());

        doReturn(new HashSet(){{add(new Category());}})
                .when(this.categoryService)
                .findCategoriesByIds(this.filterArticlesBindingModel.getSelectedCategories());

        doReturn(new HashSet(){{add(new Size());}})
                .when(this.sizeService)
                .findAllRawSizes();

        assertNotNull("ArticleOptionsResponseDto is null when no size is selected.",
                this.articleService.findFilteredArticles(CORRECT_PAGE_CNT,CORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel));
    }

    @Test
    public void testFindFilteredArticles_WitEmptySelectedCategoriesFilters_BrandsShouldNotBeNull(){
        this.filterArticlesBindingModel.setSelectedBrands(new ArrayList<>(){{add("Nike");}});
        this.filterArticlesBindingModel.setSelectedColors(new ArrayList<>(){{add("Green");}});
        this.filterArticlesBindingModel.setSelectedSizes(new ArrayList<>(){{add("M");}});
        this.filterArticlesBindingModel.setSelectedCategories(new ArrayList<>());

        doReturn(new HashSet<Brand>(){{add(new Brand());}})
                .when(this.brandService)
                .findAllBrandsByNames(this.filterArticlesBindingModel.getSelectedBrands());

        doReturn(new HashSet<Color>(){{add(new Color());}})
                .when(this.colorService)
                .findAllColorsIn(this.filterArticlesBindingModel.getSelectedColors());


        doReturn(new HashSet(){{add(new Size());}})
                .when(this.sizeService)
                .findAllSizesIn(this.filterArticlesBindingModel.getSelectedSizes());

        doReturn(new HashSet(){{add(new Category());}})
                .when(this.categoryService)
                .findAllRawCategories();

        assertNotNull("ArticleOptionsResponseDto is null when no category is selected.",
                this.articleService.findFilteredArticles(CORRECT_PAGE_CNT,CORRECT_PAGE_CONTENT_CNT, this.filterArticlesBindingModel));
    }

}
