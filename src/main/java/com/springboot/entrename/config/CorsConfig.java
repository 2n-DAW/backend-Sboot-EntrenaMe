package com.springboot.entrename.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Indica que esta clase contiene configuraciones
public class CorsConfig implements WebMvcConfigurer {
    @Override // Indica que se sobreescribe un metodo de la interfaz WebMvcConfigurer
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}