package com.springboot.entrename.api.security;

// import com.gabrielgua.realworld.api.exception.RestAccessDeniedHandler;
// import com.gabrielgua.realworld.api.exception.RestAuthenticationEntryPoint;
import jakarta.servlet.DispatcherType;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // private final SecurityFilter securityFilter;
    // private final RestAccessDeniedHandler restAccessDeniedHandler;
    // private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

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
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita la protección CSRF (para APIs REST)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Deshabilita las sesiones (para las APIs REST que usan tokens)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, PUBLIC_WRITE_ENDPOINTS).permitAll() // Permite acceso sin autenticación para POST a rutas públicas de escritura
                        .requestMatchers(HttpMethod.GET, PUBLIC_READ_ENDPOINTS).permitAll() // Permite acceso sin autenticación para GET a rutas públicas de lectura
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                .anonymous(AbstractHttpConfigurer::disable); // Deshabilita el acceso anónimo a las rutas protegidas
                // .exceptionHandling(handler -> handler
                //         .accessDeniedHandler(restAccessDeniedHandler) // Maneja el acceso denegado
                //         .authenticationEntryPoint(restAuthenticationEntryPoint)) // Maneja errores de autenticación
                // .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class); // Añade un filtro de seguridad personalizado (JWT)

        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(); // Para encriptar y verificar contraseñas
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Obtiene el AuthenticationManager por la configuración de Spring
    }


}
