package com.springboot.entrename.api.court.controller;

import com.springboot.entrename.api.court.dto.CourtDto;
import com.springboot.entrename.api.court.assembler.CourtAssembler;
import com.springboot.entrename.domain.court.service.CourtService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtsController {
    private final CourtService courtService;
    private final CourtAssembler courtAssembler;

    @GetMapping
    public CourtDto.CourtWrapper getAllCourts() {
        var courts = courtService.getAllCourts();
        return courtAssembler.toCourtsList(courts);
    }

    @GetMapping("/{slug}")
    public CourtDto getCourt(@PathVariable String slug) {
        var court = courtService.getCourt(slug);
        return courtAssembler.toCourtResponse(court);
    }
}
