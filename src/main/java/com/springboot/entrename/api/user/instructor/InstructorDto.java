package com.springboot.entrename.api.user.instructor;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class InstructorDto {
    private Long id_instructor;
    @NotNull
    private UUID id_user;
    private String nif;
    private String tlf;
    private String address;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @Nullable
        private String nif;
        @Nullable
        private String tlf;
        @Nullable
        private String address;
    }
}
