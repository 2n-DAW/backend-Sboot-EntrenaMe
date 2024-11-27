package com.springboot.entrename.domain.sport;

import java.util.List;

public interface SportService {
    List<SportEntity> getAllSports();

    // final indica que no se puede modificar dentro del cuerpo del método
    SportEntity getSport(final String slug);
}
