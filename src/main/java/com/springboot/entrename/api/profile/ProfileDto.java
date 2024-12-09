package com.springboot.entrename.api.profile;

import com.springboot.entrename.api.user.admin.AdminDto;
import com.springboot.entrename.api.user.client.ClientDto;
import com.springboot.entrename.api.user.instructor.InstructorDto;

import lombok.*;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProfileDto {
    private Long id_user;
    private String img_user;
    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @NotNull
    private String username;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AdminDto admin;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientDto client;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private InstructorDto instructor;

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
