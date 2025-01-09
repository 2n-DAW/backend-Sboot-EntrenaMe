package com.springboot.entrename.domain.inscription;

import com.springboot.entrename.domain.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionEntity, Long>, JpaSpecificationExecutor<InscriptionEntity> {
    Optional<InscriptionEntity> findBySlugInscription(String slug);

    List<InscriptionEntity> findByIdUserClientAndStateIn(UserEntity idUserClient, List<Integer> state);

    Integer countByIdUserClient_IdUserAndIdActivity_IdActivityAndStateIn(UUID idUser, Long idActivity, List<Integer> state);
}
