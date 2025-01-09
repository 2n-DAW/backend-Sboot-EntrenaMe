package com.springboot.entrename.domain.court;

import com.springboot.entrename.domain.courtHour.CourtHourEntity;
import com.springboot.entrename.domain.sport.SportEntity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

// Lombok es una librería de Java que ayuda a reducir el código repetitivo
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA
import java.util.HashSet;
import java.util.Set;
// import java.util.ArrayList;
// import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courts")
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCourt")
public class CourtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_court")
    private Long idCourt;

    @Column(name = "n_court", nullable = false, length = 255)
    private String nameCourt;

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
    private Set<SportEntity> sports = new HashSet<>();

    @OneToMany(
        mappedBy = "id_court", // mappedBy desgina propietario de la relación
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL // cascade mantiene la integridad referencial entre tablas
    )
    @JsonBackReference // Marca este lado como "referencia inversa"
    private Set<CourtHourEntity> courtsHours = new HashSet<>();
    // private List<CourtHourEntity> courtsHours = new ArrayList<>();

    @Builder
    public CourtEntity(Long idCourt, String nameCourt, String imgCourt, String slugCourt) {
        this.idCourt = idCourt;
        this.nameCourt = nameCourt;
        this.imgCourt = imgCourt;
        this.slugCourt = slugCourt;
    }
}
