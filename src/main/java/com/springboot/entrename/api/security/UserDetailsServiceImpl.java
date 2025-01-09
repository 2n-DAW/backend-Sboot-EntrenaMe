package com.springboot.entrename.api.security;

import com.springboot.entrename.domain.user.UserRepository;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

// import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
            .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        // return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
        return new UserDetailsImpl(
            user.getIdUser(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getTypeUser()))
        );
    }
}
