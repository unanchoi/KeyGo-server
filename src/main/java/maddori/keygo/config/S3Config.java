package maddori.keygo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class S3Config {
    private final Environment environment;
    private final static String region = "ap-northeast-2";
    private final String accessKey;
    private final String secretKey;

    public S3Config(Environment environment) {
        this.environment = environment;
        this.accessKey = environment.getProperty("s3.accessKey");
        this.secretKey = environment.getProperty("s3.secretKey");
    }

    @Bean
    public AmazonS3Client amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
