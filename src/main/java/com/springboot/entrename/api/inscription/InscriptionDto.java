package com.springboot.entrename.api.inscription;

import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.activity.ActivityDto;

import lombok.*;
// import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude;
// import com.fasterxml.jackson.annotation.JsonTypeInfo;
// import com.fasterxml.jackson.annotation.JsonTypeName;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class InscriptionDto {
    private Long id_inscription;
    @NotNull
    private UUID id_user_client;
    @NotNull
    private Long id_activity;
    @NotNull
    private Date date;
    private Integer state;
    private String slug_inscription;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ActivityDto activity;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InscriptionWrapper {
        private List<InscriptionDto> inscriptions;
        private Number inscriptions_count;
    }
}
