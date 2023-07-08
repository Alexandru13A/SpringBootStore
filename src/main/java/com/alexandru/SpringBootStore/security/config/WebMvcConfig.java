package com.alexandru.SpringBootStore.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("index");
        registry.addViewController("/login").setViewName("registerandlogin/login_form");
        registry.addViewController("/register").setViewName("registerandlogin/register_form");

        registry.addViewController("/shopping/admin/dashboard").setViewName("admin/home/admin_dashboard");
        registry.addViewController("/shopping/user/dashboard").setViewName("users/home/user_dashboard");

    }

}
