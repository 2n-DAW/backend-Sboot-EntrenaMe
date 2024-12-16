package com.springboot.entrename.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {
    // Errores de autenticación y autorización
    UNAUTHORIZED("Falta de credenciales o token inválido", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Acceso denegado", HttpStatus.FORBIDDEN),
    BLACKLISTED_TOKEN("Refresh Token ya existe en la Blacklist", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("Refresh Token inválido", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_SIGNATURE("Firma del Refresh Token inválida", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("Refresh Token expirado", HttpStatus.UNAUTHORIZED),
    MALFORMED_TOKEN("Refresh Token mal formado", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN("Refresh Token no compatible", HttpStatus.UNAUTHORIZED),
    ILLEGAL_ARGUMENT_TOKEN("Refresh Token está vacio o es nulo", HttpStatus.UNAUTHORIZED),

    // Errores de validación
    INVALID_INPUT("Entrada inválida", HttpStatus.BAD_REQUEST),

    // Errores de conflicto
    DUPLICATED_USERNAME("El nombre de usuario ya está en uso. Introduce otro", HttpStatus.UNPROCESSABLE_ENTITY),
    DUPLICATED_EMAIL("El correo electrónico ya está en uso. Introduce otro", HttpStatus.UNPROCESSABLE_ENTITY),

    // Errores de negocio
    LOGIN_INFO_INVALID("Los datos del login no son correctos", HttpStatus.UNPROCESSABLE_ENTITY),
    // ALREADY_FOLLOWED_USER("already followed user", HttpStatus.UNPROCESSABLE_ENTITY),
    // ALREADY_FAVORITED_ARTICLE("already followed user", HttpStatus.UNPROCESSABLE_ENTITY),

    // Errores de recursos no encontrados
    USER_NOT_FOUND("Usuario no encontrado", HttpStatus.NOT_FOUND),
    COURT_NOT_FOUND("Pista no encontrada", HttpStatus.NOT_FOUND),
    SPORT_NOT_FOUND("Deporte no encontrado", HttpStatus.NOT_FOUND),
    ACTIVITY_NOT_FOUND("Actividad no encontrada", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_NOT_FOUND("Refresh Token no encontrado", HttpStatus.NOT_FOUND),
    BLACKLIST_TOKEN_NOT_FOUND("Blacklist Token no encontrado", HttpStatus.NOT_FOUND),
    // FOLLOW_NOT_FOUND("such follow not found", HttpStatus.NOT_FOUND),
    // FAVORITE_NOT_FOUND("favorite not found", HttpStatus.NOT_FOUND),

    // Error genérico del servidor
    INTERNAL_SERVER_ERROR("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE("Error de conexión", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}
