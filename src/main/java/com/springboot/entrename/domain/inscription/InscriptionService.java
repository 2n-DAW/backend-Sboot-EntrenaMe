package com.springboot.entrename.domain.inscription;

// import com.springboot.entrename.api.inscription.InscriptionDto;
import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.user.UserEntity;

import java.util.List;
import java.util.UUID;

public interface InscriptionService {
    List<InscriptionEntity> getAllInscriptions();

    List<InscriptionEntity> getAllInscriptionsFromUser();

    InscriptionEntity getInscription(final String slug);

    Integer getInscriptionsCountByUserAndActivityAndStates(final UUID idUser, final Long idActivity, final List<Integer> states);

    InscriptionEntity createInscription(final ActivityEntity idActivity, final UserEntity user);

    InscriptionEntity updateInscriptionStatus(final InscriptionEntity inscription, final Integer state);

    InscriptionEntity deleteInscription(final String slug);
}
