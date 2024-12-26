package com.springboot.entrename.api.month;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MonthDto {
    private Long id_month;
    @NotNull
    private String n_month;
    private String slug_month;
}
