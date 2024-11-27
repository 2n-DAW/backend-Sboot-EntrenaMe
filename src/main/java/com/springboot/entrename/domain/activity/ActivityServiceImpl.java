package com.springboot.entrename.domain.activity;

import com.springboot.entrename.domain.exception.AppException;
import com.springboot.entrename.domain.exception.Error;

import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    @Transactional(readOnly = true)
    @Override  // Indica que este método implementa la definición de la interfaz
    public List<ActivityEntity> getAllActivities() {
        return activityRepository.findAllByOrderByIdActivityAsc();
    }

    @Transactional(readOnly = true)
    @Override // Indica que este método implementa la definición de la interfaz
    public ActivityEntity getActivity(String slug) {
        return activityRepository.findBySlugActivity(slug)
            .orElseThrow(() -> new AppException(Error.ACTIVITY_NOT_FOUND));
    }
}
