package com.springboot.entrename.api.user.admin;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AdminDto {
    private Long id_admin;
    @NotNull
    private Long id_user;
}
