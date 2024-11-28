package com.springboot.entrename.domain.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ActivityService {
    List<ActivityEntity> getAllActivities();

    // final indica que no se puede modificar dentro del cuerpo del método
    ActivityEntity getActivity(final String slug);

    Page<ActivityEntity> getAllActivitiesFiltered(Specification<ActivityEntity> filter, Pageable pageable);
}
