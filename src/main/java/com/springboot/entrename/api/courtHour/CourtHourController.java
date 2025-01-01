package com.springboot.entrename.api.courtHour;

import com.springboot.entrename.domain.courtHour.CourtHourService;

import lombok.RequiredArgsConstructor;
import com.springboot.entrename.api.security.authorization.CheckSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courtsHours")
@RequiredArgsConstructor
public class CourtHourController {
    private final CourtHourService courtHourService;
    private final CourtHourAssembler courtHourAssembler;

    @GetMapping
    @CheckSecurity.Public.canRead
    public CourtHourDto.CourtHourWrapper getAllCourtsHours() {
        var courtsHours = courtHourService.getAllCourtsHours();
        return courtHourAssembler.toCourtsHoursList(courtsHours);
    }

    @GetMapping("/detailed")
    @CheckSecurity.Public.canRead
    public CourtHourDto.CourtHourWrapper getAllDetailedCourtsHours() {
        var courtsHours = courtHourService.getAllCourtsHours();
        return courtHourAssembler.toDetailedCourtsHoursList(courtsHours);
    }

    @GetMapping("/available")
    @CheckSecurity.Public.canRead
    public CourtHourDto.CourtHourWrapper getAvailableCourtsHours(@RequestParam Long idCourt) {
        var courtsHours = courtHourService.getAvailableCourtsHours(idCourt);
        return courtHourAssembler.toDetailedCourtsHoursList(courtsHours);
    }
}
