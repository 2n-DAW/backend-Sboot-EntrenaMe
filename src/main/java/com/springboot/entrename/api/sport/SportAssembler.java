package com.springboot.entrename.api.sport;

import com.springboot.entrename.domain.sport.SportEntity;
import com.springboot.entrename.domain.court.CourtEntity;
import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.api.court.CourtDto;
import com.springboot.entrename.api.activity.ActivityDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SportAssembler {
    public SportDto.SportWrapper toSportsList(List<SportEntity> sportEntities) {
        var content = sportEntities.stream()
            .map(this::toSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content, sportEntities.size());
    }

    public SportDto.SportWrapper toSportsListWithCourtsAndActivities(List<SportEntity> sportEntities) {
        var content = sportEntities.stream()
            .map(this::toSportWithCourtsAndActivitiesResponse)
            .collect(Collectors.toList());

        return buildResponse(content, sportEntities.size());
    }

    public SportDto.SportWrapper toSportsListFiltered(Page<SportEntity> pageSports) {
        var content = pageSports.stream()
            .map(this::toSportResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageSports.getTotalElements());
    }

    public SportDto.SportWrapper toSportsListWithCourtsAndActivitiesFiltered(Page<SportEntity> pageSports) {
        var content = pageSports.stream()
            .map(this::toSportWithCourtsAndActivitiesResponse)
            .collect(Collectors.toList());

        return buildResponse(content, pageSports.getTotalElements());
    }

    public SportDto toSportResponse(SportEntity entity) {
        return SportDto.builder()
            .id_sport(entity.getIdSport())
            .n_sport(entity.getNameSport())
            .img_sport(entity.getImgSport())
            .slug_sport(entity.getSlugSport())
            .build();
    }

    public SportDto toSportWithCourtsAndActivitiesResponse(SportEntity sportEntity) {
        return SportDto.builder()
            .id_sport(sportEntity.getIdSport())
            .n_sport(sportEntity.getNameSport())
            .img_sport(sportEntity.getImgSport())
            .slug_sport(sportEntity.getSlugSport())
            .courts(sportEntity.getCourts().stream()
                .sorted((court1, court2) -> court1.getIdCourt().compareTo(court2.getIdCourt())) // Orden ascendente por id
                .map(this::toCourtResponse)
                .toList())
            .activities(sportEntity.getActivities().stream()
                .sorted((activity1, activity2) -> activity1.getIdActivity().compareTo(activity2.getIdActivity())) // Orden ascendente por id
                .map(this::toActivityResponse)
                .toList())
            .build();
    }

    private CourtDto toCourtResponse(CourtEntity courtEntity) {
        return CourtDto.builder()
            .id_court(courtEntity.getIdCourt())
            .n_court(courtEntity.getNameCourt())
            .img_court(courtEntity.getImgCourt())
            .slug_court(courtEntity.getSlugCourt())
            .build();
    }

    private ActivityDto toActivityResponse(ActivityEntity activityEntity) {
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

    private SportDto.SportWrapper buildResponse(List<SportDto> sports, Number totalSports) {
        return SportDto.SportWrapper.builder()
                .sports(sports)
                .sports_count(totalSports) 
                .build();
    }
}
