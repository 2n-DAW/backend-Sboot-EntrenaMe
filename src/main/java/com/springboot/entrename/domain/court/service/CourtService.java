package com.springboot.entrename.domain.court.service;

import com.springboot.entrename.domain.court.entity.CourtEntity;

import java.util.List;

public interface CourtService {
    List<CourtEntity> getAllCourts();

    // final indica que no se puede modificar dentro del cuerpo del método
    CourtEntity getCourt(final String slug);
}
