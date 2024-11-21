package com.springboot.entrename.domain.court.service;

import com.springboot.entrename.domain.court.dto.CourtDto;

import java.util.List;

// Interface que define los métodos que se pueden llamar en el dominio
public interface CourtService {
    List<CourtDto> listCourt();

    // final indica que no se puede modificar dentro del cuerpo del método
    CourtDto getCourt(final String slug);
}
