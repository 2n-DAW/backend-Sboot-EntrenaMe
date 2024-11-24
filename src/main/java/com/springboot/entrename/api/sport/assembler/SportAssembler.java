package com.springboot.entrename.api.sport.assembler;

import com.springboot.entrename.api.court.dto.CourtDto;
import com.springboot.entrename.api.sport.dto.SportDto;
import com.springboot.entrename.domain.sport.entity.SportEntity;

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
            .idSport(entity.getIdSport())
            .nameSport(entity.getNameSport())
            .imgSport(entity.getImgSport())
            .slugSport(entity.getSlugSport())
            .build();
    }

    public SportDto toSportWithCourtResponse(SportEntity entity) {
        return SportDto.builder()
            .idSport(entity.getIdSport())
            .nameSport(entity.getNameSport())
            .imgSport(entity.getImgSport())
            .slugSport(entity.getSlugSport())
            .courts(entity.getCourts().stream()
                .sorted((court1, court2) -> court1.getIdCourt().compareTo(court2.getIdCourt())) // Orden ascendente por id
                .map(courtEntity -> CourtDto.builder()
                    .idCourt(courtEntity.getIdCourt())
                    .nameCourt(courtEntity.getNameCourt())
                    .imgCourt(courtEntity.getImgCourt())
                    .slugCourt(courtEntity.getSlugCourt())
                    .build())
                .toList())
            .build();
    }

    private SportDto.SportWrapper buildResponse(List<SportDto> sports) {
        return SportDto.SportWrapper.builder()
                .sports(sports)
                .sportsCount(sports.size()) 
                .build();
    }
}
