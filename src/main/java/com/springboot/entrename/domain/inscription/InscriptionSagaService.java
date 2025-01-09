package com.springboot.entrename.domain.inscription;

import com.springboot.entrename.api.inscription.InscriptionDto;

public interface InscriptionSagaService {
    InscriptionEntity createInscriptionSaga(final InscriptionDto inscription);
}
