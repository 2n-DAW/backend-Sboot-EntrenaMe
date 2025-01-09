package com.springboot.entrename.domain.activity;

import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ActivityEntity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ActivityEntity getActivity(String slug) {
        return activityRepository.findBySlugActivity(slug)
            .orElseThrow(() -> new AppException(Error.ACTIVITY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public ActivityEntity getActivityById(Long id) {
        return activityRepository.findByIdActivity(id)
            .orElseThrow(() -> new AppException(Error.ACTIVITY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ActivityEntity> getAllActivitiesFiltered(Specification<ActivityEntity> filter, Pageable pageable) {
        return activityRepository.findAll(filter, pageable);
    }

    @Transactional
    @Override
    public ActivityEntity updateSpotsAvilableActivity(ActivityEntity activity, int spot) {
        activity.setSpots_available(activity.getSpots_available() + spot);
        return activityRepository.save(activity);
    }
}
