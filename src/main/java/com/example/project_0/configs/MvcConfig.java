package com.example.project_0.configs;

import com.example.project_0.constants.SecurityConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(SecurityConstants.ROOT_PATH).setViewName("loginPage");
        registry.addViewController(SecurityConstants.USER_PATH).setViewName("userPage");
        registry.addViewController(SecurityConstants.ADMIN_PATH).setViewName("adminPanel");
    }
}
