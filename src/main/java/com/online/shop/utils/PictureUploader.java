package com.online.shop.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class PictureUploader {

    @Value("${s3.accessKey}")
    private String s3ClientAccessKey;

    @Value("${s3.secretKey}")
    private String s3ClientSecretKey;

    @Value("${s3.bucketName}")
    private String s3BucketName;

    public String uploadPic(MultipartFile photo){
        BasicAWSCredentials credentials = new BasicAWSCredentials(this.s3ClientAccessKey, this.s3ClientSecretKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.EU_CENTRAL_1).build();
        String photoName = UUID.randomUUID() + ".jpg";

        try{
            InputStream is = photo.getInputStream();

            s3Client.putObject(new PutObjectRequest(this.s3BucketName, photoName, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

            S3Object s3Object = s3Client.getObject(new GetObjectRequest(this.s3BucketName, photoName));

            String photoUrl = s3Object.getObjectContent().getHttpRequest().getURI().toString();

            return photoUrl;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
