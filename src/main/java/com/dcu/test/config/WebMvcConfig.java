package com.dcu.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        // /upload/images/** URL로 들어오는 요청을 실제 파일 시스템의 static/images/ 디렉토리로 매핑
        resourceHandlerRegistry
                .addResourceHandler("/upload/images/**")
                .addResourceLocations("file:C:/JAVA/test/uploads/images/"); // 실제 파일 경로로 변경
    }
}
