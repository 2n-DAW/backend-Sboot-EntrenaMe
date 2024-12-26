package com.springboot.entrename.infra.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration  // Indica que esta clase contiene configuraciones
// public class CorsConfig implements WebMvcConfigurer {
//     @Override // Indica que se sobreescribe un metodo de la interfaz WebMvcConfigurer
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**") // Rutas permitidas
//                 .allowedOrigins("http://localhost:5173", "http://localhost:5174", "http://localhost:3000", "http://localhost:3001", "http://localhost:3002") // Dominios permitidos Ej: http://localhost:3000, https://mi-dominio.com
//                 .allowedMethods("*") // Métodos HTTP permitidos
//                 .allowedHeaders("*") // Headers permitidos
//                 .exposedHeaders("Authorization") // Encabezados adicionales expuestos al cliente
//                 .allowCredentials(true); // Cookies permitidas
//     }
// }

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174", "http://localhost:3000", "http://localhost:3001", "http://localhost:3002"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}