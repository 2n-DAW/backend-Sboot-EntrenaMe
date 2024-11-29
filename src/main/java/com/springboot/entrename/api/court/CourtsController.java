package com.springboot.entrename.api.court;

import com.springboot.entrename.domain.court.CourtEntity;
import com.springboot.entrename.domain.court.CourtService;
// import com.springboot.entrename.infra.spec.CourtSpecification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtsController {
    private final CourtService courtService;
    private final CourtAssembler courtAssembler;

    private static final String DEFAULT_FILTER_LIMIT = "5";
    private static final String DEFAULT_FILTER_OFFSET = "0";

    @GetMapping
    public CourtDto.CourtWrapper getAllCourts() {
        var courts = courtService.getAllCourts();
        return courtAssembler.toCourtsList(courts);
    }

    @GetMapping("/&sports")
    public CourtDto.CourtWrapper getAllCourtsWithSport() {
        var courts = courtService.getAllCourts();
        return courtAssembler.toCourtsListWithSport(courts);
    }

    @GetMapping("/{slug}")
    public CourtDto getCourt(@PathVariable String slug) {
        var court = courtService.getCourt(slug);
        return courtAssembler.toCourtResponse(court);
    }

    @GetMapping("/filtered")
    public CourtDto.CourtWrapper getAllCourtsFiltered(
            // CourtSpecification filter,
            @Join(path = "sports", alias = "s")
            @And({
                @Spec(path = "s.nameSport", params = "sport", spec = Like.class),
                @Spec(path = "nameCourt", params = "court", spec = Like.class),
            }) Specification<CourtEntity> filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {

        Pageable pageable = PageRequest.of(offset, limit);
        var courts = courtService.getAllCourtsFiltered(filter, pageable);
        return courtAssembler.toCourtsListFiltered(courts);
    }

    @GetMapping("/&sports/filtered")
    public CourtDto.CourtWrapper getAllCourtsWithSportFiltered(
            // CourtSpecification filter,
            @Join(path = "sports", alias = "s")
            @And({
                @Spec(path = "s.nameSport", params = "sport", spec = Like.class),
                @Spec(path = "nameCourt", params = "court", spec = Like.class),
            }) Specification<CourtEntity> filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {
        
        Pageable pageable = PageRequest.of(offset, limit);
        var courts = courtService.getAllCourtsFiltered(filter, pageable);
        return courtAssembler.toCourtsListWithSportFiltered(courts);
    }
}
