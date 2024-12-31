package com.springboot.entrename.domain.user;

import com.springboot.entrename.api.security.AuthUtils;
import com.springboot.entrename.api.user.UserDto;
// import com.springboot.entrename.domain.user.UserEntity.TypeUser;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    // private final AdminRepository adminRepository;
    // private final ClientRepository clientRepository;
    // private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    // @Transactional(readOnly = true)
    @Override
    public UserEntity getCurrentUser() {
        return getByEmail(authUtils.getCurrentUserEmail());
    }

    @Transactional(readOnly = true)
    // @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity getAdminUser() {
        List<UserEntity> adminUsers = userRepository.findByTypeUser(UserEntity.TypeUser.admin);

        if (!adminUsers.isEmpty()) {
            return adminUsers.get(0);
        } else {
            throw new AppException(Error.USER_NOT_FOUND);
        }
    }

    @Override
    public UserEntity updateCurrentUser(UserDto.Update update) {
        var currentUser = getByEmail(authUtils.getCurrentUserEmail());

        if (update.getImg_user() != null) {
            currentUser.setImg_user(update.getImg_user());
        }

        //! Todas las busquedas hay que hacerlas con Id, y sub token con Id para evitar conflicto al cambiar email
        if (update.getEmail() != null) {
            userRepository.findByEmail(update.getEmail())
                .filter(found -> !found.getIdUser().equals(currentUser.getIdUser()))
                .ifPresent(found -> {throw new AppException(Error.DUPLICATED_EMAIL);});
            currentUser.setEmail(update.getEmail());
        }

        if (update.getUsername() != null) {
            userRepository.findByUsername(update.getUsername())
                .filter(found -> !found.getIdUser().equals(currentUser.getIdUser()))
                .ifPresent(found -> {throw new AppException(Error.DUPLICATED_USERNAME);});
            currentUser.setUsername(update.getUsername());
        }

        if (update.getName() != null) {
            currentUser.setName(update.getName());
        }

        if (update.getSurname() != null) {
            currentUser.setSurname(update.getSurname());
        }

        if (update.getAge() != null) {
            currentUser.setAge(update.getAge());
        }

        if (update.getBio() != null) {
            currentUser.setBio(update.getBio());
        }

        if (update.getPassword() != null) {
            currentUser.setPassword(passwordEncoder.encode(update.getPassword()));
        }

        var updateUser = userRepository.save(currentUser);
        return updateUser;
    }
}
