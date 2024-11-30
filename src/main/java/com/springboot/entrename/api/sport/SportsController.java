package com.springboot.entrename.api.sport;

import com.springboot.entrename.domain.sport.SportEntity;
import com.springboot.entrename.domain.sport.SportService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@RestController
@RequestMapping("/sports")
@RequiredArgsConstructor
public class SportsController {
    private final SportService sportService;
    private final SportAssembler sportAssembler;

    private static final String DEFAULT_FILTER_LIMIT = "2";
    private static final String DEFAULT_FILTER_OFFSET = "0";

    @GetMapping
    public SportDto.SportWrapper getAllSports() {
        var sports = sportService.getAllSports();
        return sportAssembler.toSportsList(sports);
    }

    @GetMapping("/courts&activities")
    public SportDto.SportWrapper getAllSportsWithCourts() {
        var sports = sportService.getAllSports();
        return sportAssembler.toSportsListWithCourtsAndActivities(sports);
    }

    @GetMapping("/{slug}")
    public SportDto getSport(@PathVariable String slug) {
        var sport = sportService.getSport(slug);
        return sportAssembler.toSportResponse(sport);
    }

    @GetMapping("/filtered")
    public SportDto.SportWrapper getAllSportsFiltered(
            @Join(path = "courts", alias = "c")
            @And({
                @Spec(path = "c.nameCourt", params = "court", spec = Like.class),
                @Spec(path = "nameSport", params = "sport", spec = Like.class),
            }) Specification<SportEntity> filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {

        Pageable pageable = PageRequest.of(offset, limit);
        var sports = sportService.getAllSportsFiltered(filter, pageable);
        return sportAssembler.toSportsListFiltered(sports);
    }

    @GetMapping("/courts&activities/filtered")
    public SportDto.SportWrapper getAllSportsWithCourtsAndActivitiesFiltered(
        @Join(path = "courts", alias = "c")
            @And({
                @Spec(path = "c.nameCourt", params = "court", spec = Like.class),
                @Spec(path = "nameSport", params = "sport", spec = Like.class),
            }) Specification<SportEntity> filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {
        
        Pageable pageable = PageRequest.of(offset, limit);
        var sports = sportService.getAllSportsFiltered(filter, pageable);
        return sportAssembler.toSportsListWithCourtsAndActivitiesFiltered(sports);
    }
}
