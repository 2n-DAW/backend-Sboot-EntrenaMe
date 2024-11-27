package com.springboot.entrename.api.activity;

import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.user.InstructorEntity;
import com.springboot.entrename.domain.sport.SportEntity;
import com.springboot.entrename.api.user.UserDto;
import com.springboot.entrename.api.user.InstructorDto;
import com.springboot.entrename.api.sport.SportDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ActivityAssembler {
    public ActivityDto.CourtWrapper toActivitiesList(List<ActivityEntity> activityEntities) {
        var content = activityEntities.stream()
            .map(this::toActivityResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
    }

    public ActivityDto.CourtWrapper toActivitiesListWithInstructorAndSport(List<ActivityEntity> activityEntities) {
        var content = activityEntities.stream()
            .map(this::toActivityWithInstructorAndSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content);
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
            .slug_activity(activityEntity.getSlugActivity())
            .instructor(toUserResponse(activityEntity.getIdUserInstructor()))
            .sport(toSportResponse(activityEntity.getIdSport()))
            .build();
    }

    private UserDto toUserResponse(UserEntity userEntity) {
        return UserDto.builder()
            .id_user(userEntity.getIdUser())
            .img_user(userEntity.getImgUser())
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            // .instructor(toInstructorResponse(userEntity.getIdInstructor())) // Mapear datos del cliente
            .build();
    }

    // private InstructorDto toInstructorResponse(InstructorEntity instructorEntity) {
    //     return InstructorDto.builder()
    //         .id_instructor(instructorEntity.getIdInstructor())
    //         .nif(instructorEntity.getNif())
    //         .tlf(instructorEntity.getTlf())
    //         .address(instructorEntity.getAddress())
    //         .build();
    // }

    private SportDto toSportResponse(SportEntity sportEntity) {
        return SportDto.builder()
            .id_sport(sportEntity.getIdSport())
            .n_sport(sportEntity.getNameSport())
            .img_sport(sportEntity.getImgSport())
            .slug_sport(sportEntity.getSlugSport())
            .build();
    }

    private ActivityDto.CourtWrapper buildResponse(List<ActivityDto> activities) {
        return ActivityDto.CourtWrapper.builder()
                .activities(activities)
                .count_activities(activities.size()) 
                .build();
    }
}
