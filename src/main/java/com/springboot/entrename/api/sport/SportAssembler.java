package com.springboot.entrename.api.sport;

import com.springboot.entrename.api.court.CourtDto;
import com.springboot.entrename.domain.sport.SportEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SportAssembler {
    public SportDto.SportWrapper toSportsList(List<SportEntity> sportEntities) {
        var content = sportEntities.stream()
            .map(this::toSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public SportDto.SportWrapper toSportsListWithCourts(List<SportEntity> sportEntities) {
        var content = sportEntities.stream()
            .map(this::toSportWithCourtResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public SportDto toSportResponse(SportEntity entity) {
        return SportDto.builder()
            .id_sport(entity.getIdSport())
            .n_sport(entity.getNameSport())
            .img_sport(entity.getImgSport())
            .slug_sport(entity.getSlugSport())
            .build();
    }

    public SportDto toSportWithCourtResponse(SportEntity entity) {
        return SportDto.builder()
            .id_sport(entity.getIdSport())
            .n_sport(entity.getNameSport())
            .img_sport(entity.getImgSport())
            .slug_sport(entity.getSlugSport())
            .courts(entity.getCourts().stream()
                .sorted((court1, court2) -> court1.getIdCourt().compareTo(court2.getIdCourt())) // Orden ascendente por id
                .map(courtEntity -> CourtDto.builder()
                    .id_court(courtEntity.getIdCourt())
                    .n_court(courtEntity.getNameCourt())
                    .img_court(courtEntity.getImgCourt())
                    .slug_court(courtEntity.getSlugCourt())
                    .build())
                .toList())
            .build();
    }

    private SportDto.SportWrapper buildResponse(List<SportDto> sports) {
        return SportDto.SportWrapper.builder()
                .sports(sports)
                .sports_count(sports.size()) 
                .build();
    }
}
