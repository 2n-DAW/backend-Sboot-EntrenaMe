package com.springboot.entrename.domain.user.instructor;

import com.springboot.entrename.api.user.instructor.InstructorDto;
import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.domain.user.UserEntity;

public interface InstructorService {
    void saveInstructor(final UserEntity savedUser, final UserDto.Register register);

    void updateInstructor(final InstructorEntity client, final InstructorDto.Update update);
}
