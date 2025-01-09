package com.springboot.entrename.domain.courtHour;

import com.springboot.entrename.domain.court.CourtEntity;
import com.springboot.entrename.domain.hour.HourEntity;
import com.springboot.entrename.domain.month.MonthEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courts_hours")
public class CourtHourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_court_hour")
    private Long idCourtHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_court", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private CourtEntity id_court;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hour", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private HourEntity id_hour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_month", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private MonthEntity id_month;

    @Column(name = "day_number", nullable = false)
    private int day_number;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "slug_court_hour")
    private String slug_court_hour;

    @Column(name = "available", columnDefinition = "TINYINT") 
    private Integer available;
    
    @Builder
    public CourtHourEntity(Long idCourtHour, CourtEntity id_court, HourEntity id_hour, MonthEntity id_month, int day_number, int year, String slug_court_hour, Integer available) {
        this.idCourtHour = idCourtHour;
        this.id_court = id_court;
        this.id_hour = id_hour;
        this.id_month = id_month;
        this.day_number = day_number;
        this.year = year;
        this.slug_court_hour = slug_court_hour;
        this.available = available;
    }
}
