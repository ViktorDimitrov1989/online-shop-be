package com.online.shop.areas.articles.serviceImpl;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.online.shop.areas.articles.dto.article.ArticleResponseDto;
import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.entities.ArticleStatus;
import com.online.shop.areas.articles.entities.Brand;
import com.online.shop.areas.articles.models.binding.CreateArticleBindingModel;
import com.online.shop.areas.articles.repositories.ArticleRepository;
import com.online.shop.areas.articles.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    private ArticleStatusService articleStatusService;

    private BrandService brandService;

    private ColorService colorService;

    private SizeService sizeService;

    private ModelMapper modelMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              ArticleStatusService articleStatusService,
                              BrandService brandService,
                              ColorService colorService,
                              SizeService sizeService,
                              ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.articleStatusService = articleStatusService;
        this.brandService = brandService;
        this.colorService = colorService;
        this.sizeService = sizeService;
        this.modelMapper = modelMapper;
    }


    @Override
    public ArticleResponseDto createArticle(CreateArticleBindingModel createArticleBindingModel, MultipartFile photo) {
        Article article = this.modelMapper.map(createArticleBindingModel, Article.class);
        Brand brand = this.brandService.findBrandByName(createArticleBindingModel.getBrandName());

        article.setBrand(brand);

        ArticleStatus status = this.modelMapper.map(createArticleBindingModel, ArticleStatus.class);


        this.articleStatusService.createArticleStatus(status);

        //-----///
        BasicAWSCredentials creds = new BasicAWSCredentials("access_key", "secret_key");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).build();

        String bucketName = "bucket-" + UUID.randomUUID();
        String photoName = UUID.randomUUID() + ".jpg";
        s3Client.createBucket(bucketName);

        try{
            InputStream is = photo.getInputStream();

            s3Client.putObject(new PutObjectRequest(bucketName, photoName, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, photoName));

            String photoUrl = s3Object.getObjectContent().getHttpRequest().getURI().toString();

            System.out.println(photoUrl);


        } catch (IOException e) {
            e.printStackTrace();
        }

        String debug = "";


        return null;
    }
}
