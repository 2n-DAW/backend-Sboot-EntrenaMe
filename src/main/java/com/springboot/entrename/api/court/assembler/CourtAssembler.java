package com.springboot.entrename.api.court.assembler;

import com.springboot.entrename.api.court.dto.CourtDto;
import com.springboot.entrename.domain.court.entity.CourtEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourtAssembler {
    public CourtDto.CourtWrapper toCourtsList(List<CourtEntity> courtEntities) {
        var content = courtEntities.stream().map(entity -> {
            return toCourtResponse(entity);
        }).collect(Collectors.toList());

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

    private CourtDto.CourtWrapper buildResponse(List<CourtDto> courts) {
        return CourtDto.CourtWrapper.builder()
                .courts(courts)
                .build();
    }
}
