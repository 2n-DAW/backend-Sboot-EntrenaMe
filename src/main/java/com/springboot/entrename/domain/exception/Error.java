package com.springboot.entrename.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {
    DUPLICATED_USER("El nombre de usuario o el email están duplicados", HttpStatus.UNPROCESSABLE_ENTITY),
    LOGIN_INFO_INVALID("Los datos del login no son correctos", HttpStatus.UNPROCESSABLE_ENTITY),
    ALREADY_FOLLOWED_USER("already followed user", HttpStatus.UNPROCESSABLE_ENTITY),
    ALREADY_FAVORITED_ARTICLE("already followed user", HttpStatus.UNPROCESSABLE_ENTITY),

    USER_NOT_FOUND("Usuario no encontrado", HttpStatus.NOT_FOUND),
    COURT_NOT_FOUND("Pista no encontrada", HttpStatus.NOT_FOUND),
    SPORT_NOT_FOUND("Deporte no encontrado", HttpStatus.NOT_FOUND),
    ACTIVITY_NOT_FOUND("Actividad no encontrado", HttpStatus.NOT_FOUND),
    FOLLOW_NOT_FOUND("such follow not found", HttpStatus.NOT_FOUND),
    FAVORITE_NOT_FOUND("favorite not found", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus status;
}
