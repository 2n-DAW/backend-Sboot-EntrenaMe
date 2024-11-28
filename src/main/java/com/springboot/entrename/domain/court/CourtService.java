package com.springboot.entrename.domain.court;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import java.util.List;

public interface CourtService {
    Page<CourtEntity> getAllCourts(Pageable pageable);

    // final indica que no se puede modificar dentro del cuerpo del método
    CourtEntity getCourt(final String slug);
}
