package com.springboot.entrename.api.security;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.AdminEntity;
import com.springboot.entrename.domain.user.ClientEntity;
import com.springboot.entrename.domain.user.InstructorEntity;
import com.springboot.entrename.domain.user.UserEntity.TypeUser;
import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.domain.user.UserRepository;
import com.springboot.entrename.domain.user.AdminRepository;
import com.springboot.entrename.domain.user.ClientRepository;
import com.springboot.entrename.domain.user.InstructorRepository;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override  // Indica que este método implementa la definición de la interfaz
    public UserEntity register(final UserDto.Register register) {

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
            .password(passwordEncoder.encode(register.getPassword()))
            .typeUser(register.getTypeUser());
    
        // Dependiendo del tipo de usuario, establece el nombre de archivo de la imagen
        if (register.getTypeUser() == TypeUser.admin) builder.imgUser("admin.jpg");
        if (register.getTypeUser() == TypeUser.client) builder.imgUser("client.jpg");
        if (register.getTypeUser() == TypeUser.instructor) builder.imgUser("instructor.jpg");
    
        // Guarda el usuario en la base de datos
        UserEntity savedUser = userRepository.save(builder.build());
        
        // Asigna el usuario al admin, dependiendo del tipo de usuario
        if (register.getTypeUser() == TypeUser.admin) {
            AdminEntity adminEntity = AdminEntity.builder()
                .idUser(savedUser)
                .build();
            adminRepository.save(adminEntity);
            savedUser.setIdAdmin(adminEntity); // Asignar el admin al user
        }

        // Asigna el usuario al cliente y añade sus datos, dependiendo del tipo de usuario
        if (register.getTypeUser() == TypeUser.client && register.getClient() != null) {
            ClientEntity clientEntity = ClientEntity.builder()
                .idUser(savedUser)
                .nif(register.getClient().getNif())
                .tlf(register.getClient().getTlf())
                .build();
            clientRepository.save(clientEntity);
            savedUser.setIdClient(clientEntity); // Asignar el client al user
        }

        // Asigna el usuario al instructor y añade sus datos, dependiendo del tipo de usuario
        if (register.getTypeUser() == TypeUser.instructor && register.getInstructor() != null) {
            InstructorEntity instructorEntity = InstructorEntity.builder()
                .idUser(savedUser)
                .nif(register.getInstructor().getNif())
                .tlf(register.getInstructor().getTlf())
                .address(register.getInstructor().getAddress())
                .build();
            instructorRepository.save(instructorEntity);
            savedUser.setIdInstructor(instructorEntity); // Asignar el instructor al user
        }

        return savedUser;
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity login(final UserDto.Login login) {
        return userRepository.findByEmail(login.getEmail())
            .filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword()))
            .orElseThrow(() -> new AppException(Error.LOGIN_INFO_INVALID));
        
        //! Falta implementar JWT
    }
}
