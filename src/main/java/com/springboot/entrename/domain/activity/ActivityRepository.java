package com.springboot.entrename.domain.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long>, JpaSpecificationExecutor<ActivityEntity> {
    List<ActivityEntity> findAllByOrderByIdActivityAsc();

    Optional<ActivityEntity> findBySlugActivity(String slugCourt);
}
