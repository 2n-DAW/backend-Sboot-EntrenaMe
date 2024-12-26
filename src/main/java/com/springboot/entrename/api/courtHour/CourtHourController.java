package com.springboot.entrename.api.courtHour;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.springboot.entrename.domain.courtHour.CourtHourService;

@RestController
@RequestMapping("/courtsHours")
@RequiredArgsConstructor
public class CourtHourController {
    private final CourtHourService courtHourService;
    private final CourtHourAssembler courtHourAssembler;

    @GetMapping
    public CourtHourDto.CourtHourWrapper getAllCourtsHours() {
        var courtsHours = courtHourService.getAllCourtsHours();
        return courtHourAssembler.toCourtsHoursList(courtsHours);
    }

    @GetMapping("/detailed")
    public CourtHourDto.CourtHourWrapper getAllDetailedCourtsHours() {
        var courtsHours = courtHourService.getAllCourtsHours();
        return courtHourAssembler.toDetailedCourtsHoursList(courtsHours);
    }

    @GetMapping("/available")
    public CourtHourDto.CourtHourWrapper getAvailableCourtsHours(@RequestParam Long idCourt) {
        var courtsHours = courtHourService.getAvailableCourtsHours(idCourt);
        return courtHourAssembler.toDetailedCourtsHoursList(courtsHours);
    }
}
