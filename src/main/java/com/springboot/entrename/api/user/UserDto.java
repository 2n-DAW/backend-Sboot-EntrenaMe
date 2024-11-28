package com.springboot.entrename.api.user;

import com.springboot.entrename.domain.user.UserEntity.TypeUser;

import lombok.*;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;
// import com.fasterxml.jackson.annotation.JsonTypeInfo;
// import com.fasterxml.jackson.annotation.JsonTypeName;

// import java.util.List;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id_user;
    private String img_user;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private TypeUser type_user;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientDto client;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private InstructorDto instructor;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Client {
        private Long id_user;
        private String img_user;
        @NotNull
        private String email;
        @NotNull
        private String username;
        @NotNull
        private String password;
        @NotNull
        private TypeUser type_user;
        @NotNull
        private Long id_client;
        @Nullable
        private String nif;
        @Nullable
        private String tlf;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Instructor {
        private Long id_user;
        private String img_user;
        @NotNull
        private String email;
        @NotNull
        private String username;
        @NotNull
        private String password;
        @NotNull
        private TypeUser type_user;
        @NotNull
        private Long id_instructor;
        @Nullable
        private String nif;
        @Nullable
        private String tlf;
        @Nullable
        private String address;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Register {
        @NotNull
        @Email
        private String email;

        @NotNull
        @Pattern(
            regexp = "[\\w\\d]{1,30}",
            message = "El nombre de usuario debe tener una longitud entre 1 y 30 caracteres, y puede incluir mayúsculas, minúsculas, números y _"
        )
        private String username;

        @NotBlank
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,32}$",
            message = "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y números"
        )
        private String password;

        @NotNull
        private TypeUser typeUser;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Login {
        @NotNull
        @Email
        private String email;

        @NotBlank
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,32}$",
            message = "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y números"
        )
        private String password;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @Nullable
        private String img_user;
        @Nullable
        private String email;
        @Nullable
        private String username;
        @Nullable
        private String password;
    }
}
