package com.springboot.entrename.domain.refreshToken;

public interface RefreshTokenService {
    RefreshTokenEntity getRefreshToken(final Long idUser);
}
