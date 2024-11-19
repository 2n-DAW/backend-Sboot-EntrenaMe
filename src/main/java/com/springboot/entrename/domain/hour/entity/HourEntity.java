package com.springboot.entrename.domain.hour.entity;

import com.springboot.entrename.domain.court.entity.CourtHourEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

// Lombok es una librería de Java que ayuda a reducir el código repetitivo
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "hours")
public class HourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hour")
    private Long idHour;

    @Column(name = "slot_hour", nullable = false, length = 255)
    private String slotHour;

    @OneToMany(mappedBy = "hour", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference // Marca este lado como "referencia inversa"
    private List<CourtHourEntity> courtsHours = new ArrayList<>();

    @Builder
    public HourEntity(Long idHour, String slotHour) {
        this.idHour = idHour;
        this.slotHour = slotHour;
    }
}
