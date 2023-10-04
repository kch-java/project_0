package com.example.project_0.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("loginPage");
        registry.addViewController("/user").setViewName("userPage");
        registry.addViewController("/admin").setViewName("adminPanel");
    }
}
