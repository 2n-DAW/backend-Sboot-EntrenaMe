package com.springboot.entrename.domain.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long>, JpaSpecificationExecutor<RefreshTokenEntity> {

    Optional<RefreshTokenEntity> findByIdUser(Long idUser);
}
