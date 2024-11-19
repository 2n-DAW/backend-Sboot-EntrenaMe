package com.springboot.entrename.domain.court.entity;

import com.springboot.entrename.domain.sport.entity.SportEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

// Lombok es una librería de Java que ayuda a reducir el código repetitivo
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courts")
public class CourtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_court")
    private Long idCourt;

    @Column(name = "n_court", nullable = false, length = 255)
    private String nCourt;

    @Column(name = "img_court", length = 255)
    private String imgCourt;

    @Column(name = "slug_court", length = 255)
    private String slugCourt;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name = "courts_sports",
        joinColumns = @JoinColumn(name = "id_court"),
        inverseJoinColumns = @JoinColumn(name = "id_sport")
    )
    @JsonManagedReference // Marca este lado como "propietario"
    private Set<SportEntity> sports = new HashSet<>();

    // mappedBy desgina propietario de la relación
    // cascade mantiene la integridad referencial entre tablas
    @OneToMany(mappedBy = "court", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference // Marca este lado como "referencia inversa"
    private List<CourtHourEntity> courtsHours = new ArrayList<>();

    @Builder
    public CourtEntity(Long idCourt, String nCourt, String imgCourt, String slugCourt) {
        this.idCourt = idCourt;
        this.nCourt = nCourt;
        this.imgCourt = imgCourt;
        this.slugCourt = slugCourt;
    }
}
