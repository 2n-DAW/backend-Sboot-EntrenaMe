package com.springboot.entrename.api.court.assembler;

import com.springboot.entrename.api.court.dto.CourtDto;
import com.springboot.entrename.api.sport.dto.SportDto;
import com.springboot.entrename.domain.court.entity.CourtEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourtAssembler {
    public CourtDto.CourtWrapper toCourtsList(List<CourtEntity> courtEntities) {
        var content = courtEntities.stream()
            .map(this::toCourtResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public CourtDto.CourtWrapper toCourtsListWithSport(List<CourtEntity> courtEntities) {
        var content = courtEntities.stream()
            .map(this::toCourtWithSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public CourtDto toCourtResponse(CourtEntity entity) {
        return CourtDto.builder()
            .idCourt(entity.getIdCourt())
            .nameCourt(entity.getNameCourt())
            .imgCourt(entity.getImgCourt())
            .slugCourt(entity.getSlugCourt())
            .build();
    }

    public CourtDto toCourtWithSportResponse(CourtEntity entity) {
        return CourtDto.builder()
            .idCourt(entity.getIdCourt())
            .nameCourt(entity.getNameCourt())
            .imgCourt(entity.getImgCourt())
            .slugCourt(entity.getSlugCourt())
            .sports(entity.getSports().stream()
                .sorted((sport1, sport2) -> sport1.getIdSport().compareTo(sport2.getIdSport())) // Orden ascendente por id
                .map(sportEntity -> SportDto.builder()
                    .idSport(sportEntity.getIdSport())
                    .nameSport(sportEntity.getNameSport())
                    .imgSport(sportEntity.getImgSport())
                    .slugSport(sportEntity.getSlugSport())
                    .build())
                .toList())
            .build();
    }

    private CourtDto.CourtWrapper buildResponse(List<CourtDto> courts) {
        return CourtDto.CourtWrapper.builder()
                .courts(courts)
                .courtsCount(courts.size()) 
                .build();
    }
}
