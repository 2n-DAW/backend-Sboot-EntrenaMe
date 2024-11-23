package com.springboot.entrename.domain.court.repository;

import com.springboot.entrename.domain.court.entity.CourtEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
// import java.util.Optional;

@Repository
public interface CourtRepository extends JpaRepository<CourtEntity, Long>, JpaSpecificationExecutor<CourtEntity> {
    // @Query("SELECT c FROM CourtEntity c ORDER BY c.nameCourt")
    // List<CourtEntity> findAll();
    List<CourtEntity> findAllByOrderByIdCourtAsc();


    // @Query("SELECT c FROM CourtEntity c WHERE c.slugCourt = :slug")
    // CourtEntity findBySlug(@Param("slug") String slug);
    // Optional<CourtEntity> findBySlug(@Param("slug") String slug);
    CourtEntity findBySlugCourt(String slugCourt);
    
}
