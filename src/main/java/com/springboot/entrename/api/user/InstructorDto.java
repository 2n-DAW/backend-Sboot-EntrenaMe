package com.springboot.entrename.api.user;

// import com.springboot.entrename.api.user.dto.ClientDto;

import lombok.*;
import javax.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
// import com.fasterxml.jackson.annotation.JsonInclude;
// import com.springboot.entrename.api.sport.dto.SportDto;

// import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class InstructorDto {
    private Long id_instructor;
    @NotNull
    private UserDto id_user;
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
