package com.springboot.entrename.domain.refreshToken;

import java.util.UUID;

public interface RefreshTokenService {
    RefreshTokenEntity getRefreshToken(final UUID idUser);
}
