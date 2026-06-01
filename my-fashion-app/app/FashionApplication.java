package com.fashion.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FashionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionApplication.class, args);
        System.out.println("🚀 Fashion Inspiration Hub is running at: http://localhost:8080");
    }
}