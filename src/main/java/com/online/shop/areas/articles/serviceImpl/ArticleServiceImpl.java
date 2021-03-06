package com.online.shop.areas.articles.serviceImpl;
import com.online.shop.areas.articles.dto.article.ArticleOptionsResponseDto;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.dto.colors.ColorResponseDto;
import com.online.shop.areas.articles.dto.sizes.SizeResponseDto;
import com.online.shop.areas.articles.entities.*;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.enums.Status;
import com.online.shop.areas.articles.models.binding.*;
import com.online.shop.areas.articles.repositories.ArticleRepository;
import com.online.shop.areas.articles.services.*;
import com.online.shop.areas.cart.services.ShoppingCartArticleService;
import com.online.shop.exception.RequestException;
import com.online.shop.response.ResponseMessageConstants;
import com.online.shop.utils.PictureUploader;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    private ArticleStatusService articleStatusService;

    private BrandService brandService;

    private ColorService colorService;

    private CategoryService categoryService;

    private SizeService sizeService;

    private ModelMapper modelMapper;

    private PictureUploader pictureUploader;


    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              ArticleStatusService articleStatusService,
                              BrandService brandService,
                              ColorService colorService,
                              CategoryService categoryService,
                              SizeService sizeService,
                              ModelMapper modelMapper,
                              PictureUploader pictureUploader) {
        this.articleRepository = articleRepository;
        this.articleStatusService = articleStatusService;
        this.brandService = brandService;
        this.colorService = colorService;
        this.categoryService = categoryService;
        this.sizeService = sizeService;
        this.modelMapper = modelMapper;
        this.pictureUploader = pictureUploader;
    }

    public List<ArticleResponseDto> mapArticlesResponse(Iterable<Article> articles){
        List<ArticleResponseDto> resp = new ArrayList<>();

        for (Article article : articles) {
            ArticleResponseDto articleResp = this.modelMapper.map(article, ArticleResponseDto.class);

            if(articleResp != null){
                articleResp.setColors(article.getColors().stream().map(Color::getName).collect(Collectors.toSet()));
                articleResp.setSizes(article.getSizes().stream().map(Size::getName).collect(Collectors.toSet()));

                resp.add(articleResp);
            }

        }

        return resp;
    }

    @Override
    public Article getArticleById(Long id) {
        Article resp = this.articleRepository.findOneById(id);

        if(resp == null){
            throw new RequestException(ResponseMessageConstants.INVALID_ARTICLE_ID, HttpStatus.BAD_REQUEST);
        }

        return resp;
    }

    @Override
    public ArticleResponseDto createArticle(CreateArticleBindingModel createArticleBindingModel, MultipartFile photo) {
        String photoUrl = this.pictureUploader.uploadPic(photo);

        if(this.articleRepository.findAllByName(createArticleBindingModel.getName()).size() >= 1){
            throw new RequestException(ResponseMessageConstants.ARTICLE_NAME_DUPLICATE, HttpStatus.BAD_REQUEST);
        }

        Article article = this.modelMapper.map(createArticleBindingModel, Article.class);
        article.setPhoto(photoUrl);

        Brand brand = this.brandService.findBrandByName(createArticleBindingModel.getBrandName());

        article.setBrand(brand);

        ArticleStatus status = this.modelMapper.map(createArticleBindingModel, ArticleStatus.class);
        article.setSizes(this.sizeService.findAllSizesIn(createArticleBindingModel.getSizes()));
        article.setColors(this.colorService.findAllColorsIn(createArticleBindingModel.getColors()));
        article.setStatus(this.articleStatusService.createArticleStatus(status));
        article.setCategory(this.categoryService.findCategoryById(createArticleBindingModel.getCategory()));

        Article createdArticle = this.articleRepository.save(article);

        ArticleResponseDto articleResponse = this.modelMapper.map(createdArticle, ArticleResponseDto.class);
        articleResponse.setSizes(createdArticle.getSizes().stream().map(Size::getName).collect(Collectors.toSet()));
        articleResponse.setColors(createdArticle.getColors().stream().map(Color::getName).collect(Collectors.toSet()));

        return articleResponse;
    }

    @Override
    public ArticleOptionsResponseDto getArticleOptionsBySeasonAndGender(GetOptionsBindingModel getOptionsBindingModel) {
        List<CategoryResponseDto> categories = new ArrayList<>();

        if(!getOptionsBindingModel.getAll()){
            categories = this.categoryService.findAllCategories()
                    .stream().filter(c -> c.getGender().equals(getOptionsBindingModel.getGender().name())
                            && c.getSeason().equals(getOptionsBindingModel.getSeason().name()))
                    .collect(Collectors.toList());
        }else{
            categories = this.categoryService.findAllCategories();
        }

        List<BrandResponseDto> brands = this.brandService.findAllBrands();
        List<ColorResponseDto> colors = this.colorService.findAllColors();
        List<SizeResponseDto> sizes = this.sizeService.findAllSizes();

        ArticleOptionsResponseDto resp = new ArticleOptionsResponseDto(brands, categories, sizes, colors);

        return resp;
    }

    @Override
    public Page<ArticleResponseDto> findFilteredArticles(int page, int size, FilterArticlesBindingModel filterArticlesBindingModel) {

        if(page < 0 || size < 1){
            throw new RequestException(ResponseMessageConstants.INVALID_PAGE_OR_PAGE_SIZE, HttpStatus.BAD_REQUEST);
        }

        Pageable pageCount = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Set<Size> selectedSizes = this.sizeService.findAllSizesIn(filterArticlesBindingModel.getSelectedSizes());
        Set<Color> selectedColors = this.colorService.findAllColorsIn(filterArticlesBindingModel.getSelectedColors());
        Set<Brand> selectedBrands = this.brandService.findAllBrandsByNames(filterArticlesBindingModel.getSelectedBrands());
        Set<Category> selectedCategories = this.categoryService.findCategoriesByIds(filterArticlesBindingModel.getSelectedCategories());

        if(selectedSizes.size() == 0){
            selectedSizes = this.sizeService.findAllRawSizes();
        }

        if(selectedColors.size() == 0){
            selectedColors = this.colorService.findAllRawColors();
        }

        if(selectedBrands.size() == 0){
            selectedBrands = this.brandService.findAllRawBrands();
        }

        if(selectedCategories.size() == 0){
            selectedCategories = this.categoryService.findAllRawCategories();
        }

        if(filterArticlesBindingModel.getSelectedStatuses().size() == 0){
            filterArticlesBindingModel.setSelectedStatuses(Status.getStatuses());
        }

        Page<Article> articles = this.articleRepository.findAllDistinctBySizesInAndColorsInAndBrandInAndCategoryInAndCategorySeasonAndCategoryGenderAndStatusStatusIn(
                selectedSizes,
                selectedColors,
                selectedBrands,
                selectedCategories,
                filterArticlesBindingModel.getChosenSeason(),
                filterArticlesBindingModel.getChosenGender(),
                filterArticlesBindingModel.getSelectedStatuses(),
                pageCount);

        List<ArticleResponseDto> resp = this.mapArticlesResponse(articles);

        Page<ArticleResponseDto> respPage = new PageImpl<>(resp, pageCount, articles.getTotalElements());

        return respPage;
    }

    @Override
    public ArticleResponseDto editArticle(EditArticleBindingModel article, MultipartFile photo) {
        Article articleToEdit = this.getArticleById(article.getId());

        if(this.articleRepository.findAllByName(article.getName()).size() > 1){
            throw new RequestException(ResponseMessageConstants.ARTICLE_NAME_DUPLICATE, HttpStatus.BAD_REQUEST);
        }

        String updatedPhotoUrl = articleToEdit.getPhoto();

        if(photo != null){
            updatedPhotoUrl = this.pictureUploader.uploadPic(photo);
        }

        articleToEdit.setPhoto(updatedPhotoUrl);
        articleToEdit.setName(article.getName());
        articleToEdit.setPrice(article.getPrice());
        articleToEdit.setDescription(article.getDescription());
        articleToEdit.setSizes(this.sizeService.findAllSizesIn(article.getSizes()));
        articleToEdit.setColors(this.colorService.findAllColorsIn(article.getColors()));

        Article editedArticle = this.articleRepository.save(articleToEdit);

        return this.modelMapper.map(editedArticle, ArticleResponseDto.class);
    }

    @Override
    public ArticleResponseDto deleteArticleById(Long id) {
        Article articleToDelete = this.getArticleById(id);

        if(articleToDelete.getArticles().size() > 0){
            throw new RequestException(ResponseMessageConstants.ARTICLE_IS_PURCHASED, HttpStatus.BAD_REQUEST);
        }

        this.articleRepository.delete(articleToDelete);

        return this.modelMapper.map(articleToDelete, ArticleResponseDto.class);
    }

    @Override
    public List<ArticleResponseDto> getAdverts() {
        List<Article> articles = this.articleRepository.findLastAddedArticles();

        List<ArticleResponseDto> articlesResponse = this.mapArticlesResponse(articles);

        return articlesResponse;
    }

}
