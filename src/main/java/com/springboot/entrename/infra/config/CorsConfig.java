package com.springboot.entrename.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Indica que esta clase contiene configuraciones
public class CorsConfig implements WebMvcConfigurer {
    @Override // Indica que se sobreescribe un metodo de la interfaz WebMvcConfigurer
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Rutas permitidas
                .allowedOrigins("http://localhost:5173", "http://localhost:5174") // Dominios permitidos Ej: http://localhost:3000, https://mi-dominio.com
                .allowedMethods("*") // Métodos HTTP permitidos
                .allowedHeaders("*") // Headers permitidos
                .exposedHeaders("Authorization") // Encabezados adicionales expuestos al cliente
                .allowCredentials(true); // Cookies permitidas
    }
}