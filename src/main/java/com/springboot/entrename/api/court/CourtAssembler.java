package com.springboot.entrename.api.court;

import com.springboot.entrename.domain.court.CourtEntity;
import com.springboot.entrename.api.sport.SportDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourtAssembler {
    public CourtDto.CourtWrapper toCourtsList(List<CourtEntity> pageCourts) {
        var content = pageCourts.stream()
            .map(this::toCourtResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public CourtDto.CourtWrapper toCourtsListWithSport(List<CourtEntity> pageCourts) {
        var content = pageCourts.stream()
            .map(this::toCourtWithSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public CourtDto.CourtWrapper toCourtsListFiltered(Page<CourtEntity> pageCourts) {
        var content = pageCourts.stream()
            .map(this::toCourtResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public CourtDto.CourtWrapper toCourtsListWithSportFiltered(Page<CourtEntity> pageCourts) {
        var content = pageCourts.stream()
            .map(this::toCourtWithSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public CourtDto toCourtResponse(CourtEntity courtEntity) {
        return CourtDto.builder()
            .id_court(courtEntity.getIdCourt())
            .n_court(courtEntity.getNameCourt())
            .img_court(courtEntity.getImgCourt())
            .slug_court(courtEntity.getSlugCourt())
            .build();
    }

    public CourtDto toCourtWithSportResponse(CourtEntity courtEntity) {
        return CourtDto.builder()
            .id_court(courtEntity.getIdCourt())
            .n_court(courtEntity.getNameCourt())
            .img_court(courtEntity.getImgCourt())
            .slug_court(courtEntity.getSlugCourt())
            .sports(courtEntity.getSports().stream()
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
