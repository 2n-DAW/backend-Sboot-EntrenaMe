// package com.springboot.entrename.api.security;

// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.HttpStatusEntryPoint;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @RequiredArgsConstructor
// @EnableWebSecurity
// public class WebSecurityConfiguration {
//     // private final JWTAuthFilter jwtAuthFilter;

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf().disable() // Deshabilitar CSRF para pruebas
//             // .formLogin().disable()
//             .authorizeRequests()
//             // .requestMatchers("/**").permitAll() // Permite todas las rutas
//             .anyRequest().permitAll(); // Permitir todas las solicitudes sin autenticación
//             // .and()
//             // .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//             // .and()
//             // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }
// }