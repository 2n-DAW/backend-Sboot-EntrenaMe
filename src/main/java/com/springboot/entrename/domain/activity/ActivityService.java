package com.springboot.entrename.domain.activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ActivityService {
    List<ActivityEntity> getAllActivities();

    ActivityEntity getActivity(final String slug);

    ActivityEntity getActivityById(final Long id);

    Page<ActivityEntity> getAllActivitiesFiltered(Specification<ActivityEntity> filter, Pageable pageable);

    ActivityEntity updateSpotsAvilableActivity(final ActivityEntity activity, final int spot);
}
