package com.springboot.entrename.domain.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long>, JpaSpecificationExecutor<ActivityEntity> {
    Optional<ActivityEntity> findBySlugActivity(String slug);

    Optional<ActivityEntity> findByIdActivity(Long id);
}
