package com.online.shop.categories;

import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.entities.Category;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.models.binding.CreateCategoryBindingModel;
import com.online.shop.areas.articles.repositories.CategoryRepository;
import com.online.shop.areas.articles.serviceImpl.CategoryServiceImpl;
import com.online.shop.exception.RequestException;
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
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class CategoryServiceTests {

    private static final String INVALID_SEASON = "Alabala";

    private static final String INVALID_GENDER = "Alabala";

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category testRawCategory;

    private CreateCategoryBindingModel testCreateCategory;

    private CategoryResponseDto testCreatedCategoryResponse;

    private Collection<Category> testCategoryCollection;


    @Before
    public void init(){
        this.testRawCategory = new Category();
        this.testRawCategory.setGender(Gender.BOYS);
        this.testRawCategory.setId(1L);
        this.testRawCategory.setMaxAge(5);
        this.testRawCategory.setMinAge(2);
        this.testRawCategory.setName("Пуловери");
        this.testRawCategory.setSeason(Season.SPRING_SUMMER);
        this.testRawCategory.setArticles(new HashSet<>(){{add(new Article());}});

        this.testCategoryCollection = new ArrayList<>();
        this.testCategoryCollection.add(this.testRawCategory);

        this.testCreateCategory = new CreateCategoryBindingModel();
        this.testCreateCategory.setGender(Gender.BOYS.name());
        this.testCreateCategory.setMinAge(2);
        this.testCreateCategory.setMaxAge(5);
        this.testCreateCategory.setName("Пуловери");
        this.testCreateCategory.setSeason(Season.SPRING_SUMMER.name());

        this.testCreatedCategoryResponse = new CategoryResponseDto();
        this.testCreatedCategoryResponse.setGender(Gender.BOYS.name());
        this.testCreatedCategoryResponse.setMinAge(2);
        this.testCreatedCategoryResponse.setMaxAge(5);
        this.testCreatedCategoryResponse.setName("Пуловери");
        this.testCreatedCategoryResponse.setSeason(Season.SPRING_SUMMER.name());
        this.testCreatedCategoryResponse.setId(1L);

        doReturn(this.testRawCategory).when(this.categoryRepository).findOneById(this.testRawCategory.getId());
        doReturn(new HashSet<>(this.testCategoryCollection)).when(this.categoryRepository).findAllByIdIn(new ArrayList<>(){{add(testRawCategory.getId());}});
        doReturn(this.testCategoryCollection).when(this.categoryRepository).findAll();
        //doReturn(this.testRawCategory).when(this.categoryRepository).save(this.testRawCategory);
        doReturn(this.testRawCategory).when(this.categoryRepository).findOneByName(this.testRawCategory.getName());

        //doReturn(this.testRawCategory).when(this.modelMapper).map(this.testCreateCategory, Category.class);
        //doReturn(this.testCreatedCategoryResponse).when(this.modelMapper).map(this.testRawCategory, CategoryResponseDto.class);

    }

    @Test
    public void testFindCategoryById_WithValidId_ShouldNotBeNull(){
        assertNotNull("Category is null when valid id is provided.", this.categoryService.findCategoryById(1L));
    }

    @Test(expected = RequestException.class)
    public void testFindCategoryById_WithInvalidId_ShouldThrowException(){
        this.categoryService.findCategoryById(2L);
    }

    @Test
    public void testFindCategoriesWithIdsIn_WithCorrectIds_ShouldReturnNotEmptyCollection(){
        assertTrue("Categories collection is empty when there is persisted rows.",
                !this.categoryService.findCategoriesByIds(new ArrayList<>(){{add(testRawCategory.getId());}}).isEmpty());
    }

    @Test
    public void testFindCategoriesWithIdsIn_WithEmptyIds_ShouldReturnEmptyCollection(){
        assertTrue("Categories collection is not empty when desired id is missing.",
                this.categoryService.findCategoriesByIds(new ArrayList<>()).isEmpty());
    }

    @Test
    public void testFindCategoriesWithIdsIn_WithInvalidIds_ShouldReturnEmptyCollection(){
        assertTrue("Categories collection is not empty when desired id is missing.",
                this.categoryService.findCategoriesByIds(new ArrayList<>(){{add(5L);}}).isEmpty());
    }

    @Test
    public void testFindAllRawCategories_WithPersistedRows_ShouldNotBeEmpty(){
        assertTrue("Categories collection is empty when there are persisted rows.",
                !this.categoryService.findAllRawCategories().isEmpty());
    }

    @Test
    public void testFindAllRawCategories_WithNoPersistedRows_ShouldBeEmpty(){
        this.testCategoryCollection.clear();

        doReturn(this.testCategoryCollection).when(this.categoryRepository).findAll();

        assertTrue("Categories collection is not empty when there are not persisted rows.",
                this.categoryService.findAllRawCategories().isEmpty());
    }


    @Test(expected = RequestException.class)
    public void testCreateCategory_WithExistingName_ShouldThrowException(){
        this.categoryService.createCategory(this.testCreateCategory);
    }

    @Test(expected = RequestException.class)
    public void testCreateCategory_WithInvalidGender_ShouldThrowException(){
        this.testCreateCategory.setSeason(INVALID_GENDER);

        this.categoryService.createCategory(this.testCreateCategory);
    }

    @Test(expected = RequestException.class)
    public void testCreateCategory_WithInvalidSeason_ShouldThrowException(){
        this.testCreateCategory.setSeason(INVALID_SEASON);

        this.categoryService.createCategory(this.testCreateCategory);
    }

    @Test
    public void testCreateCategory_WithValidData_ShouldNotReturnNull(){
        this.testCreateCategory.setName("Some new catName");

        this.testRawCategory.setName(this.testCreateCategory.getName());
        this.testCreatedCategoryResponse.setName(this.testRawCategory.getName());

        doReturn(this.testRawCategory).when(this.categoryRepository).save(this.testRawCategory);
        doReturn(this.testRawCategory).when(this.modelMapper).map(this.testCreateCategory, Category.class);
        doReturn(this.testCreatedCategoryResponse).when(this.modelMapper).map(this.testRawCategory, CategoryResponseDto.class);

        assertNotNull("Null value was returned when valid data is provided.", this.categoryService.createCategory(this.testCreateCategory));
    }








}
