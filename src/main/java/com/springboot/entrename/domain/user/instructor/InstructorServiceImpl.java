package com.springboot.entrename.domain.user.instructor;

import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.user.instructor.InstructorDto;
import com.springboot.entrename.domain.user.UserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;

    @Transactional
    @Override
    public void saveInstructor(UserEntity savedUser, UserDto.Register register) {
        InstructorEntity instructorEntity = InstructorEntity.builder()
            .id_user(savedUser)
            .nif(register.getInstructor().getNif())
            .tlf(register.getInstructor().getTlf())
            .address(register.getInstructor().getAddress())
            .build();

        instructorRepository.save(instructorEntity);
        savedUser.setId_instructor(instructorEntity); // Asignar el instructor al user
    }

    @Transactional
    @Override
    public void updateInstructor(InstructorEntity instructor, InstructorDto.Update update) {
        if (update.getNif() != null) instructor.setNif(update.getNif());
        if (update.getTlf() != null) instructor.setTlf(update.getTlf());
        if (update.getAddress() != null) instructor.setAddress(update.getAddress());

        instructorRepository.save(instructor);
    }
}
