package com.ticketing.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ticketing.security.interceptor.AdminInterceptor;
import com.ticketing.security.interceptor.PassagerInterceptor;



@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Interceptor pour les routes admin
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login"); // Exemple d'exclusion
        
        // Interceptor pour les routes passager
        registry.addInterceptor(new PassagerInterceptor())
                .addPathPatterns("/passager/**")
                .excludePathPatterns("/passager/login");
        
    }
}