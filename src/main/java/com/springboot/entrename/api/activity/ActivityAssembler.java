package com.springboot.entrename.api.activity;

import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.api.user.UserAssembler;
import com.springboot.entrename.api.sport.SportAssembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ActivityAssembler {
    private final UserAssembler userAssembler;
    private final SportAssembler sportAssembler;

    public ActivityDto.ActivityWrapper toActivitiesList(List<ActivityEntity> activityEntities) {
        var content = activityEntities.stream()
            .map(this::toActivityResponse)
            .collect(Collectors.toList());

        return buildResponse(content, activityEntities.size());
    }

    public ActivityDto.ActivityWrapper toActivitiesListWithInstructorAndSport(List<ActivityEntity> activityEntities) {
        var content = activityEntities.stream()
            .map(this::toActivityWithInstructorAndSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content, activityEntities.size());
    }

    public ActivityDto.ActivityWrapper toActivitiesListFiltered(Page<ActivityEntity> pageActivities) {
        var content = pageActivities.stream()
            .map(this::toActivityResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageActivities.getTotalElements());
    }

    public ActivityDto.ActivityWrapper toActivitiesListWithInstructorAndSportFiltered(Page<ActivityEntity> pageActivities) {
        var content = pageActivities.stream()
            .map(this::toActivityWithInstructorAndSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageActivities.getTotalElements());
    }

    public ActivityDto toActivityResponse(ActivityEntity activityEntity) {
        return buildActivityResponse(activityEntity, false);
    }

    public ActivityDto toActivityWithInstructorAndSportResponse(ActivityEntity activityEntity) {
        return buildActivityResponse(activityEntity, true);
    }

    private ActivityDto buildActivityResponse(ActivityEntity activityEntity, boolean detailed) {
        ActivityDto.ActivityDtoBuilder builder = ActivityDto.builder()
            .id_activity(activityEntity.getIdActivity())
            .id_user_instructor(activityEntity.getIdUserInstructor().getIdUser())
            .id_sport(activityEntity.getIdSport().getIdSport())
            .n_activity(activityEntity.getNameActivity())
            .description(activityEntity.getDescription())
            .week_day(activityEntity.getWeekDay())
            .slot_hour(activityEntity.getSlotHour())
            .img_activity(activityEntity.getImgActivity())
            .spots(activityEntity.getSpots())
            .spots_available(activityEntity.getSpots_available())
            .slug_activity(activityEntity.getSlugActivity());

        if (detailed) {
            builder
                .instructor(userAssembler.toUserWithoutPassResponse(activityEntity.getIdUserInstructor()))
                .sport(sportAssembler.toSportResponse(activityEntity.getIdSport()));
        }

        return builder.build();
    }

    private ActivityDto.ActivityWrapper buildResponse(List<ActivityDto> activities, Number totalActivities) {
        return ActivityDto.ActivityWrapper.builder()
                .activities(activities)
                .activities_count(totalActivities) 
                .build();
    }
}
