package com.springboot.entrename.domain.user.instructor;

import com.springboot.entrename.api.user.instructor.InstructorDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;

    @Transactional
    @Override
    public void updateInstructor(InstructorEntity instructor, InstructorDto.Update update) {
        if (update.getNif() != null) instructor.setNif(update.getNif());
        if (update.getTlf() != null) instructor.setTlf(update.getTlf());
        if (update.getAddress() != null) instructor.setAddress(update.getAddress());

        instructorRepository.save(instructor);
    }
}
