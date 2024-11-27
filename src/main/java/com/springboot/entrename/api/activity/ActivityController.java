package com.springboot.entrename.api.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.springboot.entrename.domain.activity.ActivityService;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    private final ActivityAssembler activityAssembler;

    @GetMapping
    public ActivityDto.CourtWrapper getAllActivities() {
        var activities = activityService.getAllActivities();
        return activityAssembler.toActivitiesList(activities);
    }

    @GetMapping("/instructors&sports")
    public ActivityDto.CourtWrapper getAllActivitiesWithInstructorAndSport() {
        var activities = activityService.getAllActivities();
        return activityAssembler.toActivitiesListWithInstructorAndSport(activities);
    }

    @GetMapping("/{slug}")
    public ActivityDto getActivity(@PathVariable String slug) {
        var activity = activityService.getActivity(slug);
        return activityAssembler.toActivityResponse(activity);
    }

    @GetMapping("/instructors&sports/{slug}")
    public ActivityDto getActivityWithInstructorAndSport(@PathVariable String slug) {
        var activity = activityService.getActivity(slug);
        return activityAssembler.toActivityWithInstructorAndSportResponse(activity);
    }
}
