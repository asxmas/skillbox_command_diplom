package ru.skillbox.team13.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    @Bean
    public AmazonS3 s3(){
        AWSCredentials awsCredentials =
                new BasicAWSCredentials("AKIAR37YD3JUPCW3YZE5", "6BTVJndCFF/DmCXOKNAb9Ff344tjPr/O+pJ8gR1N");
        return AmazonS3ClientBuilder.standard()
                .withRegion("eu-west-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
