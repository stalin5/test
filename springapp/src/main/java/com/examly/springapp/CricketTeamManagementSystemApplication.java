package com.examly.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CricketTeamManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(CricketTeamManagementSystemApplication.class, args);
    }
    // Global CORS config for frontend
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("https://8081-fbdedafcaedabcdd333220481ebaccebeedctwo.premiumproject.examly.io")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
