package com.example.bloggybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import lombok.NonNull;

@SpringBootApplication
public class BloggyBackend {

    public static void main(String[] args) {
        SpringApplication.run(BloggyBackend.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry
                    .addMapping("/api/v1/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowCredentials(true)
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*");
            }
        };
    }

    
}
