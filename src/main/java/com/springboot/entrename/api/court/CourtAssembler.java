package com.springboot.entrename.api.court;

import com.springboot.entrename.domain.court.CourtEntity;
import com.springboot.entrename.api.sport.SportAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourtAssembler {
    private final SportAssembler sportAssembler;

    public CourtDto.CourtWrapper toCourtsList(List<CourtEntity> courtEntities) {
        var content = courtEntities.stream()
            .map(this::toCourtResponse)
            .collect(Collectors.toList());

        return buildResponse(content, courtEntities.size());
    }

    public CourtDto.CourtWrapper toCourtsListWithSport(List<CourtEntity> courtEntities) {
        var content = courtEntities.stream()
            .map(this::toCourtWithSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content, courtEntities.size());
    }

    public CourtDto.CourtWrapper toCourtsListFiltered(Page<CourtEntity> pageCourts) {
        var content = pageCourts.stream()
            .map(this::toCourtResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageCourts.getTotalElements());
    }

    public CourtDto.CourtWrapper toCourtsListWithSportFiltered(Page<CourtEntity> pageCourts) {
        var content = pageCourts.stream()
            .map(this::toCourtWithSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageCourts.getTotalElements());
    }

    public CourtDto toCourtResponse(CourtEntity courtEntity) {
        return buildCourt(courtEntity, false);
    }

    public CourtDto toCourtWithSportResponse(CourtEntity courtEntity) {
        return buildCourt(courtEntity, true);
    }

    private CourtDto buildCourt(CourtEntity courtEntity, boolean detailed) {
        CourtDto.CourtDtoBuilder builder = CourtDto.builder()
            .id_court(courtEntity.getIdCourt())
            .n_court(courtEntity.getNameCourt())
            .img_court(courtEntity.getImgCourt())
            .slug_court(courtEntity.getSlugCourt());

        if (detailed) {
            builder
                .sports(courtEntity.getSports().stream()
                    .sorted((sport1, sport2) -> sport1.getIdSport().compareTo(sport2.getIdSport())) // Orden ascendente por id
                    .map(sportAssembler::toSportResponse)
                    .toList());
        }

        return builder.build();
    }

    private CourtDto.CourtWrapper buildResponse(List<CourtDto> courts, Number totalCourts) {
        return CourtDto.CourtWrapper.builder()
                .courts(courts)
                .courts_count(totalCourts) 
                .build();
    }
}
