package com.online.shop.areas.articles.serviceImpl;
import com.online.shop.areas.articles.dto.article.ArticleOptionsResponseDto;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.dto.articleStatus.ArticleStatusResponseDto;
import com.online.shop.areas.articles.dto.brand.BrandResponseDto;
import com.online.shop.areas.articles.dto.category.CategoryResponseDto;
import com.online.shop.areas.articles.dto.colors.ColorResponseDto;
import com.online.shop.areas.articles.dto.sizes.SizeResponseDto;
import com.online.shop.areas.articles.entities.*;
import com.online.shop.areas.articles.models.binding.CreateArticleBindingModel;
import com.online.shop.areas.articles.models.binding.FilterArticlesBindingModel;
import com.online.shop.areas.articles.repositories.ArticleRepository;
import com.online.shop.areas.articles.services.*;
import com.online.shop.utils.PictureUploader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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


    @Override
    public ArticleResponseDto createArticle(CreateArticleBindingModel createArticleBindingModel, MultipartFile photo) {
        String photoUrl = this.pictureUploader.uploadPic(photo);
        Article article = this.modelMapper.map(createArticleBindingModel, Article.class);
        article.setPhoto(photoUrl);

        Brand brand = this.brandService.findBrandByName(createArticleBindingModel.getBrandName());

        article.setBrand(brand);

        ArticleStatus status = this.modelMapper.map(createArticleBindingModel, ArticleStatus.class);
        article.setSizes(this.sizeService.findAllSizesIn(createArticleBindingModel.getSizes()));
        article.setColors(this.colorService.findAllColorsIn(createArticleBindingModel.getColors()));
        article.setStatus(this.articleStatusService.createArticleStatus(status));
        article.setCategory(this.categoryService.findCategoryById(createArticleBindingModel.getCategory()));

        Article createdArticle  = this.articleRepository.save(article);

        ArticleResponseDto articleResponse = this.modelMapper.map(createdArticle, ArticleResponseDto.class);
        articleResponse.setSizes(createdArticle.getSizes().stream().map(Size::getName).collect(Collectors.toSet()));
        articleResponse.setColors(createdArticle.getColors().stream().map(Color::getName).collect(Collectors.toSet()));

        return articleResponse;
    }

    @Override
    public ArticleOptionsResponseDto getArticleOptions() {
        List<BrandResponseDto> brands = this.brandService.findAllBrands();
        List<CategoryResponseDto> categories = this.categoryService.findAllCategories();
        List<ColorResponseDto> colors = this.colorService.findAllColors();
        List<SizeResponseDto> sizes = this.sizeService.findAllSizes();

        ArticleOptionsResponseDto resp = new ArticleOptionsResponseDto(brands, categories, sizes, colors);

        return resp;
    }

    @Override
    public Page<ArticleResponseDto> findAllArticles(int page, int size) {
        Pageable pageCount = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Page<Article> articles = this.articleRepository.findAll(pageCount);

        List<ArticleResponseDto> resp = new ArrayList<>();

        for (Article article : articles) {
            ArticleResponseDto articleResp = this.modelMapper.map(article, ArticleResponseDto.class);

            articleResp.setColors(article.getColors().stream().map(Color::getName).collect(Collectors.toSet()));
            articleResp.setSizes(article.getSizes().stream().map(Size::getName).collect(Collectors.toSet()));

            resp.add(articleResp);
        }

        Page<ArticleResponseDto> respPage = new PageImpl<>(resp, pageCount, this.articleRepository.count());

        return respPage;
    }

    @Override
    public Page<ArticleResponseDto> findFilteredArticles(int page, int size, FilterArticlesBindingModel filterArticlesBindingModel) {
        Pageable pageCount = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Page<Article> articles = this.articleRepository.findFilteredArticles(
                filterArticlesBindingModel.getForbiddenColors(),
                filterArticlesBindingModel.getForbiddenCategories(),
                filterArticlesBindingModel.getForbiddenSizes(),
                filterArticlesBindingModel.getForbiddenBrands(),
                filterArticlesBindingModel.getChosenSeason(),
                filterArticlesBindingModel.getChosenGender(),
                pageCount);



        return null;
    }
}
