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
}
