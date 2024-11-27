package com.springboot.entrename.api.court;

import com.springboot.entrename.api.sport.SportDto;
import com.springboot.entrename.domain.court.CourtEntity;

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
            .id_court(entity.getIdCourt())
            .n_court(entity.getNameCourt())
            .img_court(entity.getImgCourt())
            .slug_court(entity.getSlugCourt())
            .build();
    }

    public CourtDto toCourtWithSportResponse(CourtEntity entity) {
        return CourtDto.builder()
            .id_court(entity.getIdCourt())
            .n_court(entity.getNameCourt())
            .img_court(entity.getImgCourt())
            .slug_court(entity.getSlugCourt())
            .sports(entity.getSports().stream()
                .sorted((sport1, sport2) -> sport1.getIdSport().compareTo(sport2.getIdSport())) // Orden ascendente por id
                .map(sportEntity -> SportDto.builder()
                    .id_sport(sportEntity.getIdSport())
                    .n_sport(sportEntity.getNameSport())
                    .img_sport(sportEntity.getImgSport())
                    .slug_sport(sportEntity.getSlugSport())
                    .build())
                .toList())
            .build();
    }

    private CourtDto.CourtWrapper buildResponse(List<CourtDto> courts) {
        return CourtDto.CourtWrapper.builder()
                .courts(courts)
                .courts_count(courts.size()) 
                .build();
    }
}
