package com.example.demo.security.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    String ACCESS_KEY;
    @Value("${cloud.aws.credentials.secret-key}")
    String SECRET_KEY;
    private String region = "ap-northeast-2";

    @Bean
    public AmazonS3Client amazonS3Client(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();

    }

}
