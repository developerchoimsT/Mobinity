package com.ms.hoopi.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@ComponentScan
public class MyCorsConfig implements WebMvcConfigurer {

    @Autowired
    private final AuthInterceptor authInterceptor;

    @Autowired
    private final AdminInterceptor adminInterceptor;

    public MyCorsConfig(AuthInterceptor authInterceptor
                        , AdminInterceptor adminInterceptor) {
        this.authInterceptor = authInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("되는 중?");
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000") // 여기에 필요한 원본 패턴을 추가
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("되는 거야...?");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/hoopi/email/**", "/api/hoopi/join/**", "/api/hoopi/phone/**", "/api/hoopi/login/**");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/**");
    }
}
