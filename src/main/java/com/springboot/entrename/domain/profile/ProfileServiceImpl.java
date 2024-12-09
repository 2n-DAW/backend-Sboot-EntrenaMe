package com.springboot.entrename.domain.profile;

import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserEntity getProfile(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
    }
}
