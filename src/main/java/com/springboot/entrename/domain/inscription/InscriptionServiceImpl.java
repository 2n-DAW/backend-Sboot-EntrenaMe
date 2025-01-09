package com.springboot.entrename.domain.inscription;

// import com.springboot.entrename.api.inscription.InscriptionDto;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.UserService;
import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.activity.ActivityService;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService {
    private final InscriptionRepository inscriptionRepository;
    private final UserService userService;
    private final ActivityService activityService;

    @Transactional(readOnly = true)
    @Override
    public List<InscriptionEntity> getAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<InscriptionEntity> getAllInscriptionsFromUser() {
        // Obtiene el usuario actual
        UserEntity user = userService.getCurrentUser();

        return inscriptionRepository.findByIdUserClientAndStateIn(user, List.of(1, 2));
    }

    @Transactional(readOnly = true)
    @Override
    public InscriptionEntity getInscription(String slug) {
        return inscriptionRepository.findBySlugInscription(slug)
            .orElseThrow(() -> new AppException(Error.INSCRIPTION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getInscriptionsCountByUserAndActivityAndStates(UUID idUser, Long idActivity, List<Integer> states) {
        return inscriptionRepository.countByIdUserClient_IdUserAndIdActivity_IdActivityAndStateIn(idUser, idActivity, states);
    }

    @Transactional
    @Override
    public InscriptionEntity createInscription(ActivityEntity idActivity, UserEntity user) {
        InscriptionEntity inscription = InscriptionEntity.builder()
            .idActivity(idActivity)
            .idUserClient(user)
            .date(Date.valueOf(LocalDate.now()))
            .state(1)
            .slugInscription("inscription-" + Instant.now())
            .build();

        return inscriptionRepository.save(inscription);
    }

    @Transactional
    @Override
    public InscriptionEntity updateInscriptionStatus(InscriptionEntity inscription, Integer state) {
        inscription.setState(state);
        return inscriptionRepository.save(inscription);
    }

    @Transactional
    @Override
    public InscriptionEntity deleteInscription(String slug) {

        // Obtiene la inscripción
        InscriptionEntity inscription = getInscription(slug);

        // Obtiene la actividad
        ActivityEntity activity = activityService.getActivityById(inscription.getIdActivity().getIdActivity());

        // Aumenta en 1 las plazas disponibles en la actividad
        activityService.updateSpotsAvilableActivity(activity, 1);

        // Marca como eliminada la inscripción
        inscription.setState(-1);
        return inscriptionRepository.save(inscription);
    }
}
