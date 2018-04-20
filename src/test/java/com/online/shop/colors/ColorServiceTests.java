package com.online.shop.colors;

import com.online.shop.areas.articles.entities.Color;
import com.online.shop.areas.articles.repositories.ColorRepository;
import com.online.shop.areas.articles.serviceImpl.ColorServiceImpl;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class ColorServiceTests {

    private static final String VALID_COLOR_NAME = "Зелен";
    private static final String INVALID_COLOR_NAME = "Бенбен";

    @Mock
    private ColorRepository colorRepository;

    @InjectMocks
    private ColorServiceImpl colorService;

    private Color testColor;

    private List<Color> testAllColors;

    private List<String> testColorNames;

    @Before
    public void init(){
        this.colorService = new ColorServiceImpl(this.colorRepository, new ModelMapper());

        this.testColor = new Color();
        testColor.setId(1L);
        testColor.setName(VALID_COLOR_NAME);

        this.testAllColors = new ArrayList<>(){{add(testColor);}};
        this.testColorNames = new ArrayList<>(){{add(testColor.getName());}};

        doReturn(this.testColor).when(this.colorRepository).findOneByName(VALID_COLOR_NAME);
    }

    @Test
    public void testFindAllColorsIn_WithValidData_ShouldNotBeEmpty(){
        doReturn(new HashSet<>(this.testAllColors)).when(this.colorRepository).findAllByNameIn(this.testColorNames);

        assertTrue("Colors collection is empty.", !this.colorService.findAllColorsIn(this.testColorNames).isEmpty());
    }

    @Test
    public void testFindAllColorsIn_WithInvalidData_ShouldBeEmpty(){
        doReturn(new HashSet<>()).when(this.colorRepository).findAllByNameIn(new HashSet<>());

        assertTrue("Colors collection is not empty.", this.colorService.findAllColorsIn(new HashSet<>()).isEmpty());
    }

    @Test
    public void testFindColorByName_WithValidData_ShouldNotBeNull(){
        assertNotNull("Color is not null.", this.colorService.findColorByName(VALID_COLOR_NAME));
    }

    @Test(expected = RequestException.class)
    public void testFindColorByName_WithInvalidData_ShouldThrowException(){
        assertNotNull("Color is not null.", this.colorService.findColorByName(INVALID_COLOR_NAME));
    }

    @Test
    public void testFindAllColors_ShouldNotBeEmptyCollection(){
        doReturn(this.testAllColors).when(this.colorRepository).findAll();

        assertNotNull("Color is not null.", this.colorService.findAllColors());
    }

    @Test
    public void testFindAllColors_ShouldBeEmptyCollection(){
        this.testAllColors.clear();

        doReturn(this.testAllColors).when(this.colorRepository).findAll();

        assertTrue("Colors collection is not empty.", this.colorService.findAllColors().isEmpty());
    }



}
