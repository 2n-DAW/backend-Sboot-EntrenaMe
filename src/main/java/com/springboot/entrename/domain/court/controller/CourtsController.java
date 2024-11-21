package com.springboot.entrename.domain.court.controller;

import com.springboot.entrename.domain.court.dto.CourtDto;
import com.springboot.entrename.domain.court.service.CourtService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtsController {
    private final CourtService courtService;

    @GetMapping
    public CourtDto.MultipleCourt listCourts() {
        return CourtDto.MultipleCourt.builder().courts(courtService.listCourt()).build();
    }

    @GetMapping("/{slug}")
    public CourtDto.SingleCourt<CourtDto> getCourt(@PathVariable String slug) {
        return new CourtDto.SingleCourt<>(courtService.getCourt(slug));
    }

    
}
