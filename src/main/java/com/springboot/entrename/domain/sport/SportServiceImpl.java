package com.springboot.entrename.domain.sport;

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
public class SportServiceImpl implements SportService {
    private final SportRepository sportRepository;

    @Transactional(readOnly = true)
    @Override  // Indica que este método implementa la definición de la interfaz
    public List<SportEntity> getAllSports() {
        return sportRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override // Indica que este método implementa la definición de la interfaz
    public SportEntity getSport(String slug) {
        return sportRepository.findBySlugSport(slug)
            .orElseThrow(() -> new AppException(Error.SPORT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override  // Indica que este método implementa la definición de la interfaz
    public Page<SportEntity> getAllSportsFiltered(Specification<SportEntity> filter, Pageable pageable) {
        return sportRepository.findAll(filter, pageable);
    }
}
