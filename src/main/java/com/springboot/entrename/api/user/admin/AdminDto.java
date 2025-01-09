package com.springboot.entrename.api.user.admin;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AdminDto {
    private Long id_admin;
    @NotNull
    private UUID id_user;
}
