package com.springboot.entrename.api.security.jwt;

import com.springboot.entrename.domain.user.UserEntity.TypeUser;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

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

@Component
public class JWTUtils {
    private final Key accessTokenKey;
    private final Long accessTokenExpiration;
    private final Key refreshTokenKey;
    private final Long refreshTokenExpiration;

    // Constructor
    public JWTUtils(
        @Value("${api.security.accesstoken.secret}") String accessKey,
        @Value("${api.security.accesstoken.expiration}") Long accessExpiration,
        @Value("${api.security.refreshtoken.secret}") String refreshKey,
        @Value("${api.security.refreshtoken.expiration}") Long refreshExpiration
    ) {
        this.accessTokenKey = Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessExpiration;
        this.refreshTokenKey = Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));
        this.refreshTokenExpiration = refreshExpiration;
    }

    // Crea JWT
    public String generateJWT(Long idUser, String email, String username, TypeUser typeUser, String tokenType) {
        if (idUser == null || email == null || email.isEmpty() || username == null || username.isEmpty() || typeUser == null) {
            throw new AppException(Error.ILLEGAL_ARGUMENT_TOKEN);
        }

        Key key = "access".equals(tokenType) ? accessTokenKey : refreshTokenKey;
        Long expiration = "access".equals(tokenType) ? accessTokenExpiration : refreshTokenExpiration;

        Instant exp = Instant.now(); // Momento actual
        return Jwts.builder()
            .setSubject(idUser.toString())
            .claim("email", email)
            .claim("username", username)
            .claim("typeUser", typeUser)
            .setIssuedAt(new Date(exp.toEpochMilli()))
            .setExpiration(new Date(exp.toEpochMilli() + expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact(); // Genera JWT serializado
    }

    // Valida JWT
    public boolean validateJWT(String jwt, String tokenType) {
        try {
            Key key = "access".equals(tokenType) ? accessTokenKey : refreshTokenKey;

            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt) // Analiza y valida el token JWT. Si el token es inválido lanza una JwtException
                .getBody();
            Instant now = Instant.now(); // Momento actual
            Date exp = claims.getExpiration(); // Recupera la fecha de expiración del token
            return exp.after(Date.from(now)); // Devuelve boolean dependiendo si la f.expiración es posterior al momento actual

        } catch (SignatureException e) {
            throw new AppException(Error.INVALID_TOKEN_SIGNATURE);
        } catch (MalformedJwtException e) {
            throw new AppException(Error.MALFORMED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new AppException(Error.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new AppException(Error.ILLEGAL_ARGUMENT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new AppException(Error.EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new AppException(Error.INVALID_TOKEN);
        }
    }

    // Extrae el sub (identificador) de un JWT válido
    public String validateJwtAndgetEmail(String jwt, String tokenType) {
        try {
            Key key = "access".equals(tokenType) ? accessTokenKey : refreshTokenKey;
            
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

            return claims.get("email", String.class);

        } catch (SignatureException e) {
            throw new AppException(Error.INVALID_TOKEN_SIGNATURE);
        } catch (MalformedJwtException e) {
            throw new AppException(Error.MALFORMED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new AppException(Error.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new AppException(Error.ILLEGAL_ARGUMENT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw e; // Propaga esta excepción para manejarla en el filtro
        } catch (JwtException e) {
            throw new AppException(Error.INVALID_TOKEN);
        }
    }
}
