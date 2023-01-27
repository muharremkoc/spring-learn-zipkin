package com.example.springlearnzipkin;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin2.ZipkinRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class SpringLearnZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLearnZipkinApplication.class, args);
    }


    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
