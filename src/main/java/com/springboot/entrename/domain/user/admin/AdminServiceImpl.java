package com.springboot.entrename.domain.user.admin;

import com.springboot.entrename.domain.user.UserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Transactional
    @Override
    public void saveAdmin(UserEntity savedUser) {
        AdminEntity adminEntity = AdminEntity.builder()
            .id_user(savedUser)
            .build();

        adminRepository.save(adminEntity);
        savedUser.setId_admin(adminEntity); // Asignar el admin al user
    }
}
