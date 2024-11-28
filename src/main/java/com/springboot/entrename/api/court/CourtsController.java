package com.springboot.entrename.api.court;

import com.springboot.entrename.domain.court.CourtService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtsController {
    private final CourtService courtService;
    private final CourtAssembler courtAssembler;

    private static final String DEFAULT_FILTER_LIMIT = "5";
    private static final String DEFAULT_FILTER_OFFSET = "0";

    @GetMapping
    public CourtDto.CourtWrapper getAllCourts(
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {

        Pageable pageable = PageRequest.of(offset, limit);
        var courts = courtService.getAllCourts(pageable);
        return courtAssembler.toCourtsList(courts);
    }

    @GetMapping("/&sports")
    public CourtDto.CourtWrapper getAllCourtsWithSport(
        @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
        @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {
        
        Pageable pageable = PageRequest.of(offset, limit);
        var courts = courtService.getAllCourts(pageable);
        return courtAssembler.toCourtsListWithSport(courts);
    }

    @GetMapping("/{slug}")
    public CourtDto getCourt(@PathVariable String slug) {
        var court = courtService.getCourt(slug);
        return courtAssembler.toCourtResponse(court);
    }
}
