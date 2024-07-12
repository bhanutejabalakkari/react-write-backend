package com.example.bloggybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(value = "*")
public class BloggyBackend {

    public static void main(String[] args) {
        SpringApplication.run(BloggyBackend.class, args);
    }

    
}
