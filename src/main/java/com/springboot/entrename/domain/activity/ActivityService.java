package com.springboot.entrename.domain.activity;

import java.util.List;

public interface ActivityService {
    List<ActivityEntity> getAllActivities();

    // final indica que no se puede modificar dentro del cuerpo del método
    ActivityEntity getActivity(final String slug);
}
