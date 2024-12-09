package com.springboot.entrename.domain.profile;

// import com.springboot.entrename.api.security.AuthUtils;
// import com.springboot.entrename.api.user.UserDto;
// import com.springboot.entrename.domain.user.UserEntity.TypeUser;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    // private final AdminRepository adminRepository;
    // private final ClientRepository clientRepository;
    // private final InstructorRepository instructorRepository;
    // private final PasswordEncoder passwordEncoder;
    // private final AuthUtils authUtils;

    @Transactional(readOnly = true)
    @Override
    public UserEntity getProfile(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
    }

    // @Override
    // public UserEntity updateCurrentUser(UserDto.Update update) {
    //     var currentUser = getByEmail(authUtils.getCurrentUserEmail());

    //     if (update.getImg_user() != null) {
    //         currentUser.setImgUser(update.getImg_user());
    //     }

    //     //! Todas las busquedas hay que hacerlas con Id, y sub token con Id para evitar conflicto al cambiar email
    //     if (update.getEmail() != null) {
    //         userRepository.findByEmail(update.getEmail())
    //             .filter(found -> !found.getIdUser().equals(currentUser.getIdUser()))
    //             .ifPresent(found -> {throw new AppException(Error.DUPLICATED_EMAIL);});
    //         currentUser.setEmail(update.getEmail());
    //     }

    //     if (update.getUsername() != null) {
    //         userRepository.findByUsername(update.getUsername())
    //             .filter(found -> !found.getIdUser().equals(currentUser.getIdUser()))
    //             .ifPresent(found -> {throw new AppException(Error.DUPLICATED_USERNAME);});
    //         currentUser.setUsername(update.getUsername());
    //     }

    //     if (update.getPassword() != null) {
    //         currentUser.setPassword(passwordEncoder.encode(update.getPassword()));
    //     }

    //     var updateUser = userRepository.save(currentUser);
    //     return updateUser;
    // }
}
