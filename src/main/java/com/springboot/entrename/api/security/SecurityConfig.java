package com.springboot.entrename.api.security;

// import com.springboot.entrename.api.exception.CustomAuthenticationEntryPoint;
// import com.springboot.entrename.api.exception.CustomAccessDeniedHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    // private final JWTAuthFilter jwtAuthFilter;
    // private final CustomAccessDeniedHandler customAccessDeniedHandler;
    // private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final String[] PUBLIC_READ_ENDPOINTS = {
            "/courts", "/courts/*", "/courts/**",
            "/sports", "/sports/*", "/sports/**",
            "/activities", "/activities/*", "/activities/**"
    };

    private static final String[] PUBLIC_WRITE_ENDPOINTS = {
            "/users/register", "/users/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita la protección CSRF ya que utilizamos JWT en vez de cookies
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Deshabilita las sesiones (para las APIs REST que usan tokens)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, PUBLIC_WRITE_ENDPOINTS).permitAll() // Permite acceso sin autenticación para POST a rutas públicas de escritura
                        .requestMatchers(HttpMethod.GET, PUBLIC_READ_ENDPOINTS).permitAll() // Permite acceso sin autenticación para GET a rutas públicas de lectura
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .anonymous(AbstractHttpConfigurer::disable); // Deshabilita el acceso anónimo a las rutas protegidas
                // .exceptionHandling(handler -> handler
                //     .accessDeniedHandler(customAccessDeniedHandler) // Maneja errores de autorización para el acceso (usuarios autenticados pero sin permisos)
                //     .authenticationEntryPoint(customAuthenticationEntryPoint)); // Maneja errores de autenticación (usuarios no autenticados)
                // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Añade un filtro de seguridad personalizado (JWT)

        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(); // Para encriptar y verificar contraseñas
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Obtiene el AuthenticationManager a través de la configuración predeterminadade Spring Boot
        // Accede de forma predeterminada al AuthenticationProvider, que a su ver accede al UserDetailsService (userDetailsServiceImpl)
        return configuration.getAuthenticationManager();
    }
}
