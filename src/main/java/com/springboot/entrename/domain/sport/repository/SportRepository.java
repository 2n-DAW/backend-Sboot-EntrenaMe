package com.springboot.entrename.domain.sport.repository;

import com.springboot.entrename.domain.sport.entity.SportEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
// import java.util.Optional;

@Repository
public interface SportRepository extends JpaRepository<SportEntity, Long>, JpaSpecificationExecutor<SportEntity> {
    List<SportEntity> findAllByOrderByIdSportAsc();

    SportEntity findBySlugSport(String slugSport);
    
}
