package com.springboot.entrename.api.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.springboot.entrename.domain.sport.SportService;

@RestController
@RequestMapping("/sports")
@RequiredArgsConstructor
public class SportsController {
    private final SportService sportService;
    private final SportAssembler sportAssembler;

    @GetMapping
    public SportDto.SportWrapper getAllSports() {
        var sports = sportService.getAllSports();
        return sportAssembler.toSportsList(sports);
    }

    @GetMapping("/&courts")
    public SportDto.SportWrapper getAllSportsWithCourts() {
        var sports = sportService.getAllSports();
        return sportAssembler.toSportsListWithCourts(sports);
    }

    @GetMapping("/{slug}")
    public SportDto getSport(@PathVariable String slug) {
        var sport = sportService.getSport(slug);
        return sportAssembler.toSportResponse(sport);
    }
}
