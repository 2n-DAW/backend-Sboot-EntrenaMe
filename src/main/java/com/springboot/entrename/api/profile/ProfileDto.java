package com.springboot.entrename.api.profile;

import com.springboot.entrename.api.user.admin.AdminDto;
import com.springboot.entrename.api.user.client.ClientDto;
import com.springboot.entrename.api.user.instructor.InstructorDto;

import lombok.*;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProfileDto {
    private UUID id_user;
    private String img_user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer age;
    private String bio;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AdminDto admin;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientDto client;
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private InstructorDto instructor;
    private Boolean is_followed;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileWrapper {
        private List<ProfileDto> profiles;
        private Number profiles_count;
    }
}
