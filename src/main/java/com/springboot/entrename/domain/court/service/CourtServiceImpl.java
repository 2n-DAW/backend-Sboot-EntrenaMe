package com.springboot.entrename.domain.court.service;

import com.springboot.entrename.domain.court.repository.CourtRepository;
import com.springboot.entrename.domain.court.entity.CourtEntity;
import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {
    private final CourtRepository courtRepository;

    @Transactional(readOnly = true)
    @Override  // Indica que este método implementa la definición de la interfaz
    public List<CourtEntity> getAllCourts() {
        return courtRepository.findAllByOrderByIdCourtAsc();
    }

    @Transactional(readOnly = true)
    @Override // Indica que este método implementa la definición de la interfaz
    public CourtEntity getCourt(String slug) {
        return courtRepository.findBySlugCourt(slug)
            .orElseThrow(() -> new AppException(Error.COURT_NOT_FOUND));
    }
}
