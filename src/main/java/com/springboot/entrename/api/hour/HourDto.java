package com.springboot.entrename.api.hour;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HourDto {
    private Long id_hour;
    @NotNull
    private String slot_hour;
}
