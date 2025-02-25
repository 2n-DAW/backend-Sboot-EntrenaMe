package com.springboot.entrename.api.activity;

import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.activity.ActivityService;

import lombok.RequiredArgsConstructor;
import com.springboot.entrename.api.security.authorization.CheckSecurity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    private final ActivityAssembler activityAssembler;

    private static final String DEFAULT_FILTER_LIMIT = "2";
    private static final String DEFAULT_FILTER_OFFSET = "0";

    @GetMapping
    @CheckSecurity.Public.canRead
    public ActivityDto.ActivityWrapper getAllActivities() {
        var activities = activityService.getAllActivities();
        return activityAssembler.toActivitiesList(activities);
    }

    @GetMapping("/instructors&sports")
    @CheckSecurity.Public.canRead
    public ActivityDto.ActivityWrapper getAllActivitiesWithInstructorAndSport() {
        var activities = activityService.getAllActivities();
        return activityAssembler.toActivitiesListWithInstructorAndSport(activities);
    }

    @GetMapping("/{slug}")
    @CheckSecurity.Public.canRead
    public ActivityDto getActivity(@PathVariable String slug) {
        var activity = activityService.getActivity(slug);
        return activityAssembler.toActivityResponse(activity);
    }

    @GetMapping("/instructors&sports/{slug}")
    @CheckSecurity.Public.canRead
    public ActivityDto getActivityWithInstructorAndSport(@PathVariable String slug) {
        var activity = activityService.getActivity(slug);
        return activityAssembler.toActivityWithInstructorAndSportResponse(activity);
    }

    @GetMapping("/filtered")
    @CheckSecurity.Public.canRead
    public ActivityDto.ActivityWrapper getAllActivitieFiltered(
            @Join(path = "idUserInstructor", alias = "i")
            @Join(path = "idSport", alias = "s")
            @And({
                @Spec(path = "i.username", params = "instructor", spec = Like.class),
                @Spec(path = "s.slugSport", params = "sport", spec = Like.class),
                @Spec(path = "nameActivity", params = "n_activity", spec = Like.class),
                @Spec(path = "slotHour", params = "slot_hour", spec = Like.class),
                @Spec(path = "weekDay", params = "week_day", spec = Like.class),
            }) Specification<ActivityEntity> filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {

        Pageable pageable = PageRequest.of(offset, limit);
        var activities = activityService.getAllActivitiesFiltered(filter, pageable);
        return activityAssembler.toActivitiesListFiltered(activities);
    }

    @GetMapping("/instructors&sports/filtered")
    @CheckSecurity.Public.canRead
    public ActivityDto.ActivityWrapper getAllActivitiesWithInstructorAndSportFiltered(
        @Join(path = "idUserInstructor", alias = "i")
        @Join(path = "idSport", alias = "s")
        @And({
            @Spec(path = "i.username", params = "instructor", spec = Like.class),
            @Spec(path = "s.slugSport", params = "sport", spec = Like.class),
            @Spec(path = "nameActivity", params = "n_activity", spec = Like.class),
            @Spec(path = "slotHour", params = "slot_hour", spec = Like.class),
            @Spec(path = "weekDay", params = "week_day", spec = Like.class),
        }) Specification<ActivityEntity> filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {
        
        Pageable pageable = PageRequest.of(offset, limit);
        var activities = activityService.getAllActivitiesFiltered(filter, pageable);
        return activityAssembler.toActivitiesListWithInstructorAndSportFiltered(activities);
    }
}
