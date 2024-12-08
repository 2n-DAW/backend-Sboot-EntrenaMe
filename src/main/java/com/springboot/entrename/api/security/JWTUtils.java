package com.springboot.entrename.api.security;

import com.springboot.entrename.domain.user.UserEntity.TypeUser;

import io.jsonwebtoken.Claims; // Representa el Payload de un JWT
import io.jsonwebtoken.Jwts; // Para creación y validación de JWT
import io.jsonwebtoken.security.Keys; // Genera claves de firma para JWT

 // Para generar excepciones de JWTs no válidos
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.ExpiredJwtException;

import java.nio.charset.StandardCharsets; // Define el conjunto de caracteres UTF-8
import java.security.Key; // Representa la clave de firma de un JWT
import java.time.Instant; // Maneja marcas de tiempo actuales en UTC
import java.util.Date; // Para trabajar con fechas tradicionales en Java

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

// Para debugear en consola
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JWTUtils {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class); // Para debugear en consola
    private final Key jwtSecret;
    private final Long jwtExpirationMs;

    // Constructor
    public JWTUtils(@Value("${api.security.token.secret}") String secretKey, @Value("${api.security.token.expiration}") Long jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtSecret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Crea JWT
    public String generateJWT(String sub, String username, TypeUser typeUser) {
        if (sub == null || sub.equals("")) {
            return null;
        }
        Instant exp = Instant.now(); // Momento actual
        return Jwts.builder()
            .setSubject(sub)
            .claim("username", username)
            .claim("typeUser", typeUser)
            .setIssuedAt(new Date(exp.toEpochMilli()))
            .setExpiration(new Date(exp.toEpochMilli() + jwtExpirationMs))
            .signWith(jwtSecret,  SignatureAlgorithm.HS256)
            .compact(); // Genera JWT serializado
    }

    // Valida JWT
    public boolean validateJWT(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(jwt) // Analiza y valida el token JWT. Si el token es inválido lanza una JwtException
                .getBody();
            Instant now = Instant.now(); // Momento actual
            Date exp = claims.getExpiration(); // Recupera la fecha de expiración del token
            return exp.after(Date.from(now)); // Devuelve boolean dependiendo si la f.expiración es posterior al momento actual

        } catch (SignatureException e) {
            logger.error("La firma del token no es válida: " + e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("El token está mal formado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("El token no es compatible: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("El token está vacío o es nulo: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("El token ha expirado: " + e.getMessage());
        } catch (JwtException e) {
            logger.error("Error general de JWT: " + e.getMessage());
        }
        return false;
    }

    // Extrae el sub (identificador) de un JWT válido
    public String getUsernameFromJWT(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}
