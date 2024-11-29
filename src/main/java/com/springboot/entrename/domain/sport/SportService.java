package com.springboot.entrename.domain.sport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SportService {
    List<SportEntity> getAllSports();

    // final indica que no se puede modificar dentro del cuerpo del método
    SportEntity getSport(final String slug);

    Page<SportEntity> getAllSportsFiltered(Specification<SportEntity> filter, Pageable pageable);
}
