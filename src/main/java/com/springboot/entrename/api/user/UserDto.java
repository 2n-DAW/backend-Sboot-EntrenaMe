package com.springboot.entrename.api.user;

import com.springboot.entrename.api.user.admin.AdminDto;
import com.springboot.entrename.api.user.client.ClientDto;
import com.springboot.entrename.api.user.instructor.InstructorDto;
import com.springboot.entrename.domain.user.UserEntity.TypeUser;

import lombok.*;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;
// import com.fasterxml.jackson.annotation.JsonTypeInfo;
// import com.fasterxml.jackson.annotation.JsonTypeName;

// import java.util.List;
import jakarta.validation.constraints.*;

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
    private AdminDto admin;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientDto client;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private InstructorDto instructor;

    // @Getter
    // @Setter
    // @AllArgsConstructor
    // @Builder
    // public static class Client {
    //     private Long id_user;
    //     private String img_user;
    //     @NotNull
    //     private String email;
    //     @NotNull
    //     private String username;
    //     @NotNull
    //     private String password;
    //     @NotNull
    //     private TypeUser type_user;
    //     @NotNull
    //     private Long id_client;
    //     @Nullable
    //     private String nif;
    //     @Nullable
    //     private String tlf;
    // }

    // @Getter
    // @Setter
    // @AllArgsConstructor
    // @Builder
    // public static class Instructor {
    //     private Long id_user;
    //     private String img_user;
    //     @NotNull
    //     private String email;
    //     @NotNull
    //     private String username;
    //     @NotNull
    //     private String password;
    //     @NotNull
    //     private TypeUser type_user;
    //     @NotNull
    //     private Long id_instructor;
    //     @Nullable
    //     private String nif;
    //     @Nullable
    //     private String tlf;
    //     @Nullable
    //     private String address;
    // }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Register {
        @NotNull(message = "El email no puede ser nulo")
        @Email(message = "El email debe tener un formato válido")
        private String email;

        @NotNull(message = "El nombre de usuario no puede ser nulo")
        @Pattern(
            regexp = "[\\w\\d]{1,30}",
            message = "El nombre de usuario debe tener entre 1 y 30 caracteres, y puede incluir mayúsculas, minúsculas, números y _"
        )
        private String username;

        @NotBlank(message = "La contraseña no puede ser nula o vacía")
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,32}$",
            message = "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y números"
        )
        private String password;

        @NotNull(message = "El tipo de usuario no puede ser nulo")
        private TypeUser typeUser;

        private AdminDto admin;
        private ClientDto client;
        private InstructorDto instructor;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Login {
        @NotNull(message = "El email no puede ser nulo")
        @Email(message = "El email debe tener un formato válido")
        private String email;

        @NotBlank(message = "La contraseña no puede ser nula o vacía")
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,32}$",
            message = "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y números"
        )
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserWithToken {
        private Long id_user;
        private String img_user;
        private String email;
        private String username;
        private String password;
        private TypeUser type_user;
        private String token;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private AdminDto admin;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private ClientDto client;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private InstructorDto instructor;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @Nullable
        @Pattern(
            regexp = "^[a-zA-Z0-9_-]+\\.(jpg|jpeg|png|gif|tiff|svg|bmp|webp|avif)$",
            message = "El archivo de la imagen debe tener un formato válido"
        )
        private String img_user;

        @Nullable
        @Email(message = "El email debe tener un formato válido")
        private String email;

        @Nullable
        @Pattern(
            regexp = "[\\w\\d]{1,30}",
            message = "El nombre de usuario debe tener entre 1 y 30 caracteres, y puede incluir mayúsculas, minúsculas, números y _"
        )
        private String username;

        @Nullable
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,32}$",
            message = "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y números"
        )
        private String password;
    }
}
