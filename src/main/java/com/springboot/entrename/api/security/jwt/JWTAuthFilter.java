package com.springboot.entrename.api.security.jwt;

import com.springboot.entrename.domain.refreshToken.RefreshTokenService;
import com.springboot.entrename.domain.user.UserEntity.TypeUser;
import com.springboot.entrename.domain.blacklistToken.BlacklistTokenService;
import com.springboot.entrename.domain.exception.AppException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.entrename.api.security.AuthUtils;
import com.springboot.entrename.api.security.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

import com.springboot.entrename.domain.exception.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
    private final AuthUtils authUtils;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RefreshTokenService refreshTokenService;
    private final BlacklistTokenService blacklistTokenService;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Verificación del autheader
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtención del access token
        final String accessToken = authHeader.substring(TOKEN_PREFIX.length());

        try {
            validateAccessToken(request, accessToken);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            handleExpiredToken(request, response, filterChain, e);
        } catch (AppException e) {
            logger.error("AppException durante la validación del token: {}", e.getMessage());
            request.setAttribute("APP_EXCEPTION", e);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Excepción inesperada durante la validación del token", e);
            request.setAttribute("APP_EXCEPTION", new AppException(Error.UNAUTHORIZED));
            filterChain.doFilter(request, response);
        }
    }

    private void validateAccessToken(HttpServletRequest request, String accessToken) {
        // Validación del token y obtención del email
        String email = jwtUtils.validateJwtAndgetEmail(accessToken, "access");

        if (email != null && !authUtils.isAuthenticated()) {
            var userDetails = userDetailsServiceImpl.loadUserByUsername(email);

            // Establece la autenticación en el contexto de seguridad
            if (userDetails != null) {
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, ExpiredJwtException e) throws IOException, ServletException {
        // Busca el refreshToken asociado
        final String idUser = e.getClaims().getSubject();
        final String typeUser = e.getClaims().get("typeUser", String.class);
        final String refreshToken = getRefreshToken(idUser);
        logger.info("Refresh Token: {}", refreshToken);

        // Verifica para tipo de usuario client que el refreshToken no esté en la blacklist
        if ("client".equals(typeUser) && blacklistTokenService.isBlacklisted(refreshToken)) {
            logger.warn("Intento de uso de Blacklist Token: {}", refreshToken);
            handleAppException(request, response,filterChain, new AppException(Error.BLACKLISTED_TOKEN));
            return;
        }

        // Valida el Refresh Token
        try {
            jwtUtils.validateJWT(refreshToken, "refresh");
        } catch (AppException ex) {
            if ("client".equals(typeUser)) blacklistTokenService.saveBlacklistToken(refreshToken);
            logger.warn("Refresh Token inválido: {}", refreshToken);
            handleAppException(request, response, filterChain, ex);
            return;
        }

        generateAndSendNewAccessToken(response, idUser, e);
    }

    private String getRefreshToken(String idUser) {
        var refreshTokenEntity = refreshTokenService.getRefreshToken(Long.parseLong(idUser));
        return refreshTokenEntity.getRefreshToken();
    }

    private void generateAndSendNewAccessToken(HttpServletResponse response, String idUser, ExpiredJwtException e) throws IOException {
        var newAccessToken = jwtUtils.generateJWT(
            Long.parseLong(idUser),
            e.getClaims().get("email", String.class),
            e.getClaims().get("username", String.class),
            TypeUser.valueOf(e.getClaims().get("typeUser", String.class)),
            "access"
        );
        logger.info("New Access Token: {}", newAccessToken);

        // Escribe la respuesta y termina el procesamiento
        response.setHeader("New-Access-Token", newAccessToken);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), Map.of("message", "Nuevo Access Token generado"));
    }

    private void handleAppException(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, AppException e) throws IOException, ServletException {
        request.setAttribute("APP_EXCEPTION", e);
        filterChain.doFilter(request, response);
    }
}
