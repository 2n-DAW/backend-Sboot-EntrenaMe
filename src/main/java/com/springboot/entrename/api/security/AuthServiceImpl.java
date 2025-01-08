package com.springboot.entrename.api.security;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.user.UserAssembler;
import com.springboot.entrename.domain.user.UserRepository;
import com.springboot.entrename.domain.user.admin.AdminService;
import com.springboot.entrename.domain.user.client.ClientService;
import com.springboot.entrename.domain.user.instructor.InstructorService;
import com.springboot.entrename.domain.blacklistToken.BlacklistTokenEntity;
import com.springboot.entrename.domain.blacklistToken.BlacklistTokenRepository;
import com.springboot.entrename.domain.refreshToken.RefreshTokenEntity;
import com.springboot.entrename.domain.refreshToken.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.entrename.api.security.jwt.JWTUtils;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final ClientService clientService;
    private final InstructorService instructorService;
    private final UserAssembler userAssembler;
    private final BlacklistTokenRepository blacklistTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthUtils authUtils;
    private final WebClient laravelWebClient;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override  // Indica que este método implementa la definición de la interfaz
    public UserEntity register(final UserDto.Register register) {
        UserEntity savedUser;

        // Verifica si ya existe un usuario con el mismo username
        userRepository.findByUsername(register.getUsername())
            .ifPresent(username -> {
                throw new AppException(Error.DUPLICATED_USERNAME);  // Error específico para username duplicado
            });

        // Verifica si ya existe un usuario con el mismo email
        userRepository.findByEmail(register.getEmail())
            .ifPresent(email -> {
                throw new AppException(Error.DUPLICATED_EMAIL);  // Error específico para email duplicado
            });

        UserEntity.UserEntityBuilder builder = UserEntity.builder()
            .username(register.getUsername())
            .email(register.getEmail())
            .name(register.getName())
            .surname(register.getSurname())
            .birthday(register.getBirthday())
            .bio(register.getBio())
            .password(passwordEncoder.encode(register.getPassword()))
            .typeUser(register.getType_user())
            .is_active(0)
            .is_deleted(0);
    
        switch (register.getType_user()) {
            case admin:
                builder.img_user("admin.jpg");
                savedUser = userRepository.save(builder.build());
                adminService.saveAdmin(savedUser);
                break;
            case client:
                builder.img_user("client.jpg");
                savedUser = userRepository.save(builder.build());
                clientService.saveClient(savedUser, register);
                break;
            case instructor:
                builder.img_user("instructor.jpg");
                savedUser = userRepository.save(builder.build());
                instructorService.saveInstructor(savedUser, register);
                break;
            default:
                savedUser = userRepository.save(builder.build());
        }

        return savedUser;
    }

    @Transactional()
    @Override
    public UserDto springbootLogin(final UserDto.Login login) {
        
        var user = userRepository.findByEmail(login.getEmail())
            .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new AppException(Error.PASSWORD_INVALID);
        }

        // Si el usuario no está activo, lo activa
        if (user.getIs_active() == 0) {
            user.setIs_active(1);
            userRepository.save(user);
        }

        var accessToken = jwtUtils.generateJWT(user.getIdUser(), user.getEmail(), user.getUsername(), user.getTypeUser(), "access");
        var refreshToken = jwtUtils.generateJWT(user.getIdUser(), user.getEmail(), user.getUsername(), user.getTypeUser(), "refresh");
        System.out.println("AccessToken ========================================================\n" + accessToken);
        System.out.println("RefreshToken ========================================================\n" + refreshToken);

        // Inserta o actualiza el refreshToken en la base de datos
        if (refreshToken != null && refreshToken != "") {
            var existingToken = refreshTokenRepository.findByIdUser(user.getIdUser());
            RefreshTokenEntity refreshTokenEntity = existingToken.orElse(
                RefreshTokenEntity.builder()
                    .idUser(user.getIdUser())
                    .build()
            );
            refreshTokenEntity.setRefreshToken(refreshToken);
            refreshTokenRepository.saveAndFlush(refreshTokenEntity);
        }

        return userAssembler.toLoginResponse(user, accessToken);
    }

    @Override
    public UserDto laravelLogin(final UserDto.Login login) {
        // System.out.println("Email login ============================================" + login.getEmail());
        try {
            ResponseEntity<Object> response = laravelWebClient.post()
                .uri("/user/login")
                .bodyValue(login) // Enviar cuerpo con las credenciales de inicio de sesión
                .retrieve()
                .toEntity(Object.class)
                .block(); // Bloquea hasta recibir respuesta (útil para simplificar lógica)
            // System.out.println("Response WebClient ============================================" + response.getBody());

            // Convertir la respuesta al DTO esperado
            return objectMapper.convertValue(response.getBody(), UserDto.class);

        } catch (WebClientResponseException e) {
            throw new AppException(Error.LOGIN_INFO_INVALID);
        } catch (Exception e) {
            throw new AppException(Error.SERVICE_UNAVAILABLE);
        }
    }

    @Transactional()
    @Override
    public UserEntity getTypeUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
    }

    @Transactional()
    @Override
    public BlacklistTokenEntity saveBlacklistToken() {
        UUID idUser = authUtils.getCurrentUserId();

        var refreshTokenEntity = refreshTokenRepository.findByIdUser(idUser).orElseThrow(() -> new AppException(Error.REFRESH_TOKEN_NOT_FOUND));
        String refresToken = refreshTokenEntity.getRefreshToken();

        var blacklistToken  = blacklistTokenRepository.findByRefreshToken(refresToken);
        if (blacklistToken.isEmpty()) {
            BlacklistTokenEntity blacklistTokenEntity = BlacklistTokenEntity.builder()
                .refreshToken(refresToken)
                .build();
            return blacklistTokenRepository.save(blacklistTokenEntity);
        }

        return null;
    }
}
