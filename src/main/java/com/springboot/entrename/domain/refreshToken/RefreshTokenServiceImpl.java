package com.springboot.entrename.domain.refreshToken;

import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshTokenEntity getRefreshToken(Long idUser) {
        return refreshTokenRepository.findByIdUser(idUser).orElseThrow(() -> new AppException(Error.REFRESH_TOKEN_NOT_FOUND));
    }
}
