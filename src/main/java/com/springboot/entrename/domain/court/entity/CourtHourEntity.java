package com.springboot.entrename.domain.court.entity;

import com.springboot.entrename.domain.hour.entity.HourEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

// Lombok es una librería de Java que ayuda a reducir el código repetitivo
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

    // @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_court", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private CourtEntity court;

    // @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hour", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private HourEntity hour;

    @Column(name = "day_number", nullable = false)
    private int dayNumber;

    @Column(name = "slug_court_hour")
    private String slugCourtHour;
    
    @Builder
    public CourtHourEntity(Long idCourtHour, int dayNumber, String slugCourtHour) {
        this.idCourtHour = idCourtHour;
        this.dayNumber = dayNumber;
        this.slugCourtHour = slugCourtHour;
    }
}
