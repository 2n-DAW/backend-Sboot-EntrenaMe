package com.springboot.entrename.domain.court.service;

import com.springboot.entrename.domain.court.dto.CourtDto;
import com.springboot.entrename.domain.court.entity.CourtEntity;
import com.springboot.entrename.domain.court.repository.CourtRepository;

// import com.springboot.entrename.exception.AppException;
// import com.springboot.entrename.exception.Error;
import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {
    private final CourtRepository courtRepository;

    @Transactional(readOnly = true)
    @Override  // Indica que este método implementa la definición de la interfaz
    public List<CourtDto> listCourt() {
        List<CourtEntity> courtEntities = courtRepository.findList();
        return convertToCourtList(courtEntities);
    }

    private List<CourtDto> convertToCourtList(List<CourtEntity> courtEntities) {
        return courtEntities.stream().map(entity -> {
            return convertEntityToDto(entity);
        }).collect(Collectors.toList());
    }

    @Override // Indica que este método implementa la definición de la interfaz
    public CourtDto getCourt(String slug) {
        // Buscar el artículo por su slug
        CourtEntity found = courtRepository.findBySlug(slug);
        
        // CourtEntity found = courtRepository.findBySlug(slug)
        //     .orElseThrow(() -> new AppException(Error.COURT_NOT_FOUND));
    
        // Convertir la entidad en un DTO
        return convertEntityToDto(found);
    }

    private CourtDto convertEntityToDto(CourtEntity entity) {
        return CourtDto.builder()
            .nameCourt(entity.getNameCourt())
            .imgCourt(entity.getImgCourt())
            .slugCourt(entity.getSlugCourt())
            .build();
    } 
}
