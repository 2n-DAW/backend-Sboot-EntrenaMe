package com.springboot.entrename.api.activity;

import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.sport.SportEntity;
import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.sport.SportDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ActivityAssembler {
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
        return ActivityDto.builder()
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
            .slug_activity(activityEntity.getSlugActivity())
            .build();
    }

    public ActivityDto toActivityWithInstructorAndSportResponse(ActivityEntity activityEntity) {
        return ActivityDto.builder()
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
            .slug_activity(activityEntity.getSlugActivity())
            .instructor(toUserResponse(activityEntity.getIdUserInstructor()))
            .sport(toSportResponse(activityEntity.getIdSport()))
            .build();
    }

    private UserDto toUserResponse(UserEntity userEntity) {
        return UserDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImg_user())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .type_user(userEntity.getTypeUser())
            .build();
    }

    private SportDto toSportResponse(SportEntity sportEntity) {
        return SportDto.builder()
            .id_sport(sportEntity.getIdSport())
            .n_sport(sportEntity.getNameSport())
            .img_sport(sportEntity.getImgSport())
            .slug_sport(sportEntity.getSlugSport())
            .build();
    }

    private ActivityDto.ActivityWrapper buildResponse(List<ActivityDto> activities, Number totalActivities) {
        return ActivityDto.ActivityWrapper.builder()
                .activities(activities)
                .activities_count(totalActivities) 
                .build();
    }
}
