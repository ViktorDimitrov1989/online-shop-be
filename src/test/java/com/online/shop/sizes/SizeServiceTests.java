package com.online.shop.sizes;

import com.online.shop.areas.articles.entities.Size;
import com.online.shop.areas.articles.repositories.SizeRepository;
import com.online.shop.areas.articles.serviceImpl.SizeServiceImpl;
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
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class SizeServiceTests {

    private static final String VALID_SIZE_NAME = "M";
    private static final String INVALID_SIZE_NAME = "Алабала";

    @Mock
    private SizeRepository sizeRepository;

    @InjectMocks
    private SizeServiceImpl sizeService;

    private Size testSize;

    private List<Size> testAllSizes;

    private List<String> testSizeNames;

    @Before
    public void init(){
        this.sizeService = new SizeServiceImpl(this.sizeRepository, new ModelMapper());

        this.testSize = new Size();
        this.testSize.setId(1L);
        this.testSize.setName(VALID_SIZE_NAME);

        this.testAllSizes = new ArrayList<>(){{add(testSize);}};
        this.testSizeNames = new ArrayList<>(){{add(testSize.getName());}};

        doReturn(this.testSize).when(this.sizeRepository).findOneByName(VALID_SIZE_NAME);
    }

    @Test
    public void testFindAllSizesIn_WithValidData_ShouldNotBeEmpty(){
        doReturn(new HashSet<>(this.testAllSizes)).when(this.sizeRepository).findAllByNameIn(this.testSizeNames);

        assertTrue("Sizes collection is empty.", !this.sizeService.findAllSizesIn(this.testSizeNames).isEmpty());
    }

    @Test
    public void testFindAllSizesIn_WithInvalidData_ShouldBeEmpty(){
        doReturn(new HashSet<>()).when(this.sizeRepository).findAllByNameIn(new HashSet<>());

        assertTrue("Sizes collection is not empty.", this.sizeService.findAllSizesIn(new HashSet<>()).isEmpty());
    }

    @Test
    public void testFindSizeByName_WithValidData_ShouldNotBeNull(){
        assertNotNull("Size is not null.", this.sizeService.findSizeByName(VALID_SIZE_NAME));
    }

    @Test(expected = RequestException.class)
    public void testFindSizeByName_WithInvalidData_ShouldThrowException(){
        this.sizeService.findSizeByName(INVALID_SIZE_NAME);
    }

    @Test
    public void testFindAllSizes_ShouldNotBeEmptyCollection(){
        doReturn(this.testAllSizes).when(this.sizeRepository).findAll();

        assertNotNull("Size is not null.", this.sizeService.findAllSizes());
    }

    @Test
    public void testFindAllSizes_ShouldBeEmptyCollection(){
        this.testAllSizes.clear();

        doReturn(this.testAllSizes).when(this.sizeRepository).findAll();

        assertTrue("Sizes collection is not empty.", this.sizeService.findAllSizes().isEmpty());
    }



}
