package com.springboot.entrename.domain.courtHour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourtHourRepository extends JpaRepository<CourtHourEntity, Long>, JpaSpecificationExecutor<CourtHourEntity> {
    @Query("SELECT c FROM CourtHourEntity c " +
        "WHERE c.id_court.idCourt = :id_court " +
        "AND c.year >= :year " +
        "AND c.id_month.idMonth IN :id_months " +
        "AND c.day_number >= :day_number")
        // "AND c.id_hour.idHour >= :id_hour")
    @EntityGraph(attributePaths = {"id_court", "id_hour", "id_month"}) // Carga mas optima que LAZY LOADING a través de JOIN
    List<CourtHourEntity> findById_courtAndYearAndId_monthInAndDay_numberGreaterThanEqual(
        @Param("id_court") Long id_court,
        @Param("year") int year,
        @Param("id_months") List<Long> months,
        @Param("day_number") int day_number);
        // @Param("id_hour") Long id_hour);

    Optional<CourtHourEntity> findByIdCourtHourAndAvailable(Long idCourtHour, Integer available);
}
