package com.springboot.entrename.domain.court;

// import com.springboot.entrename.infra.spec.CourtSpecification;

import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        return courtRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override // Indica que este método implementa la definición de la interfaz
    public CourtEntity getCourt(String slug) {
        return courtRepository.findBySlugCourt(slug)
            .orElseThrow(() -> new AppException(Error.COURT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override  // Indica que este método implementa la definición de la interfaz
    // public Page<CourtEntity> getAllCourts(CourtSpecification filter, Pageable pageable) {
    //     return courtRepository.findAllByOrderByIdCourtAsc(filter, pageable);
    // }
    public Page<CourtEntity> getAllCourtsFiltered(Specification<CourtEntity> filter, Pageable pageable) {
        return courtRepository.findAll(filter, pageable);
    }
}
