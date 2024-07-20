package com.example.bloggybackend.configs;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.bloggybackend.services.CustomUserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    // @Bean
    // @Autowired
    // public UserDetailsService userDetailsService(CustomUserDetailsService customUserDetailsService) {
    //     return customUserDetailsService;
    // }

}
