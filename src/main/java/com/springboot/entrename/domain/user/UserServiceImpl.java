package com.springboot.entrename.domain.user;

import com.springboot.entrename.api.user.client.ClientAssembler;
import com.springboot.entrename.api.user.instructor.InstructorAssembler;
import com.springboot.entrename.domain.user.client.ClientService;
import com.springboot.entrename.domain.user.instructor.InstructorService;

import com.springboot.entrename.api.security.AuthUtils;
import com.springboot.entrename.api.user.UserDto;
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
    private final ClientAssembler clientAssembler;
    private final ClientService clientService;
    private final InstructorAssembler instructorAssembler;
    private final InstructorService instructorService;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    @Transactional(readOnly = true)
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

    @Transactional
    @Override
    public UserEntity updateCurrentUser(UserDto.Update update) {
        UserEntity currentUser = getByEmail(authUtils.getCurrentUserEmail());

        if (update.getNif() != null || update.getTlf() != null || update.getAddress() != null) {
            switch (currentUser.getTypeUser()) {
                case admin -> {}
                case client -> clientService.updateClient(currentUser.getId_client(), clientAssembler.toClientUpdate(update));
                case instructor -> instructorService.updateInstructor(currentUser.getId_instructor(), instructorAssembler.toInstructorUpdate(update));
                default -> throw new AppException(Error.INVALID_TYPE_USER);
            }
        }

        if (update.getUsername() != null) {
            userRepository.findByUsername(update.getUsername())
                .filter(found -> !found.getIdUser().equals(currentUser.getIdUser()))
                .ifPresent(found -> {throw new AppException(Error.DUPLICATED_USERNAME);});
            currentUser.setUsername(update.getUsername());
        }
        if (update.getImg_user() != null) currentUser.setImg_user(update.getImg_user());
        if (update.getName() != null) currentUser.setName(update.getName());
        if (update.getSurname() != null) currentUser.setSurname(update.getSurname());
        if (update.getBirthday() != null) currentUser.setBirthday(update.getBirthday());
        if (update.getBio() != null) currentUser.setBio(update.getBio());
        if (update.getPassword() != null) currentUser.setPassword(passwordEncoder.encode(update.getPassword()));

        return userRepository.save(currentUser);
    }
}
