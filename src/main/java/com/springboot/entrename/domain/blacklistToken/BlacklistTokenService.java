package com.springboot.entrename.domain.blacklistToken;

public interface BlacklistTokenService {
    boolean isBlacklisted(final String refreshToken);

    BlacklistTokenEntity saveBlacklistToken(final String refreshToken);
}
