package com.springboot.entrename.api.user.client;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ClientDto {
    private Long id_client;
    @NotNull
    private Long id_user;
    private String nif;
    private String tlf;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientUpdate {
        @Nullable
        private String nif;
        @Nullable
        private String tlf;
    }
}
