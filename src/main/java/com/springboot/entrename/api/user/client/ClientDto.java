package com.springboot.entrename.api.user.client;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ClientDto {
    private Long id_client;
    @NotNull
    private UUID id_user;
    private String nif;
    private String tlf;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @Nullable
        private String nif;
        @Nullable
        private String tlf;
    }
}
