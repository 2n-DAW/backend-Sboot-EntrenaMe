package com.springboot.entrename.domain.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportRepository extends JpaRepository<SportEntity, Long>, JpaSpecificationExecutor<SportEntity> {
    List<SportEntity> findAllByOrderByIdSportAsc();

    Optional<SportEntity> findBySlugSport(String slugSport);
}
