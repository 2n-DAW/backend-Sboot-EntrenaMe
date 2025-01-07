package com.springboot.entrename.api.user.instructor;

import com.springboot.entrename.api.user.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstructorAssembler {
    public InstructorDto.Update toInstructorUpdate(UserDto.Update userUpdate) {
        return InstructorDto.Update.builder()
            .nif(userUpdate.getNif())
            .tlf(userUpdate.getTlf())
            .address(userUpdate.getAddress())
            .build();
    }
}
