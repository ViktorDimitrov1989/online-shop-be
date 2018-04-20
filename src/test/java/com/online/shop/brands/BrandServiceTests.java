package com.online.shop.brands;

import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.entities.Brand;
import com.online.shop.areas.articles.models.binding.CreateBrandBindingModel;
import com.online.shop.areas.articles.repositories.BrandRepository;
import com.online.shop.areas.articles.serviceImpl.BrandServiceImpl;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class BrandServiceTests {

    private static final String WRONG_BRAND_NAME = "Alabala";

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BrandServiceImpl brandService;


    private CreateBrandBindingModel testCreatedBrand;

    private Brand testRawBrand;

    private BrandResponseDto testBrandToReturn;

    private List<Brand> testAllBrandsCollection;

    private ModelMapper realModelMapper;

    @Before
    public void init(){
        this.realModelMapper = new ModelMapper();

        this.testCreatedBrand = new CreateBrandBindingModel();
        this.testCreatedBrand.setDescription("it to make a type specimen book. It has survived not only five centuries, but also the lea");
        this.testCreatedBrand.setName("Nike");

        this.testRawBrand = new Brand();
        this.testRawBrand.setDescription("it to make a type specimen book. It has survived not only five centuries, but also the lea");
        this.testRawBrand.setName("Nike");
        this.testRawBrand.setId(1L);

        this.testBrandToReturn = new BrandResponseDto();
        this.testBrandToReturn.setDescription(this.testRawBrand.getDescription());
        this.testBrandToReturn.setName(this.testRawBrand.getName());

        this.testAllBrandsCollection = new ArrayList<>();
        this.testAllBrandsCollection.add(this.testRawBrand);

        doReturn(this.testAllBrandsCollection).when(this.brandRepository).findAll();
        doReturn(this.testRawBrand).when(this.brandRepository).findOneByName(this.testRawBrand.getName());
        doReturn(new HashSet<>(testAllBrandsCollection)).when(this.brandRepository).findAllByNameIn(new HashSet<>(){{add(testRawBrand.getName());}});

        Type targetListType = new TypeToken<List<BrandResponseDto>>() {}.getType();
        doReturn(this.realModelMapper.map(this.testAllBrandsCollection, targetListType)).when(this.modelMapper).map(this.testAllBrandsCollection, targetListType);


    }

    @Test
    public void testCreateArticleStatus_WithCorrectData_ShouldNotReturnNull(){
        this.testCreatedBrand.setName("Some new brand Name");

        this.testRawBrand.setId(null);
        this.testRawBrand.setName(this.testCreatedBrand.getName());

        doReturn(this.realModelMapper.map(this.testCreatedBrand, Brand.class)).when(this.modelMapper).map(this.testCreatedBrand, Brand.class);

        Brand  createdBrand = this.realModelMapper.map(this.testCreatedBrand, Brand.class);

        doReturn(createdBrand).when(this.brandRepository).save(any());
        createdBrand.setId(1L);

        BrandResponseDto brandResponse = this.realModelMapper.map(this.testCreatedBrand, BrandResponseDto.class);
        doReturn(brandResponse).when(this.modelMapper).map(createdBrand, BrandResponseDto.class);

        BrandResponseDto resp = this.brandService.createBrand(this.testCreatedBrand);

        assertNotNull("Returned brand response is null.", resp);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateArticleStatus_WithDuplicateBrandName_ShouldThrowException(){
        this.brandService.createBrand(this.testCreatedBrand);
    }

    @Test
    public void testGetAllBrands_ShouldNotBeNull(){
        assertNotNull("Find all brands functionality returns null.", this.brandService.findAllBrands());
    }

    @Test
    public void testGetAllBrands_WithOnePersistedBrand_ShouldNotBeEmpty(){
        assertTrue("Find all brands functionality returns empty collection instead collection with size = 1.", !this.brandService.findAllBrands().isEmpty());
    }

    @Test
    public void testGetAllBrands_WithNoPersistedBrands_ShouldBeEmpty(){
        this.testAllBrandsCollection.clear();

        Type targetListType = new TypeToken<List<BrandResponseDto>>() {}.getType();
        doReturn(this.realModelMapper.map(this.testAllBrandsCollection, targetListType)).when(this.modelMapper).map(this.testAllBrandsCollection, targetListType);

        List<BrandResponseDto> respBrands = this.brandService.findAllBrands();

        assertTrue("Find all brands functionality returns not empty collection instead collection with size = 0.", respBrands.isEmpty());
    }

    @Test
    public void testGetBrandByName_WithCorrectBrandName_ShouldNotBeNull(){
        assertNotNull("Fetched brand is null with correct brand name.", this.brandService.findBrandByName(this.testRawBrand.getName()));
    }

    @Test(expected = RequestException.class)
    public void testGetBrandByName_WithInCorrectBrandName_ShouldBeNull(){
        assertNull("Fetched brand is not null with inCorrect brand name.", this.brandService.findBrandByName(WRONG_BRAND_NAME));
    }

    @Test
    public void testFindAllRawBrands_WithNoPersistedBrands_ShouldBeEmpty(){
        this.testAllBrandsCollection.clear();

        doReturn(this.testAllBrandsCollection).when(this.brandRepository).findAll();

        assertTrue("Find all raw brands method return not empty collection, when no rows are persisted.",
                this.brandService.findAllRawBrands().isEmpty());
    }

    @Test
    public void testFindAllRawBrands_WithPersistedBrands_ShouldNotBeEmpty(){
        assertTrue("Find all raw brands method return empty collection, when one is persisted.",
                !this.brandService.findAllRawBrands().isEmpty());
    }

    @Test
    public void testFindAllBrandsByNames_WithPersistedBrands_ShouldNotBeEmpty(){
        assertTrue("Find all brands by names IN method return empty collection, when one is persisted.",
                !this.brandService.findAllBrandsByNames(new HashSet<>(){{add("Nike");}}).isEmpty());
    }

    @Test
    public void testFindAllBrandsByNames_WithNoPersistedBrands_ShouldBeEmpty(){
        this.testAllBrandsCollection.clear();

        doReturn(new HashSet<>(this.testAllBrandsCollection)).when(this.brandRepository).findAllByNameIn(new HashSet<>(){{add(testRawBrand.getName());}});

        assertTrue("Find all raw brands by names IN method return non empty collection, when there is no persisted rows.",
                this.brandService.findAllBrandsByNames(new HashSet<>(){{add("Nike");}}).isEmpty());
    }






}
