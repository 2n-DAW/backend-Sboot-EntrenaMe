package com.springboot.entrename.domain.court;

// import com.springboot.entrename.infra.spec.CourtSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CourtService {
    List<CourtEntity> getAllCourts();

    // final indica que no se puede modificar dentro del cuerpo del método
    CourtEntity getCourt(final String slug);

    // Page<CourtEntity> getAllCourts(CourtSpecification filter, Pageable pageable);
    Page<CourtEntity> getAllCourtsFiltered(Specification<CourtEntity> filter, Pageable pageable);
}
