package com.springboot.entrename.api.user;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class InstructorDto {
    private Long id_instructor;
    @NotNull
    private Long id_user;
    private String nif;
    private String tlf;
    private String address;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientUpdate {
        @Nullable
        private String nif;
        @Nullable
        private String tlf;
        @Nullable
        private String address;
    }
}
