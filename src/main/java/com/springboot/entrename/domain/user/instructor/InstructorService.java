package com.springboot.entrename.domain.user.instructor;

import com.springboot.entrename.api.user.instructor.InstructorDto;

public interface InstructorService {
    void updateInstructor(final InstructorEntity client, final InstructorDto.Update update);
}
