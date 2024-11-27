package com.springboot.entrename.domain.sport;

import com.springboot.entrename.domain.court.CourtEntity;
import com.springboot.entrename.domain.activity.ActivityEntity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonBackReference;

// Lombok es una librería de Java que ayuda a reducir el código repetitivo
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sports")
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idSport")
public class SportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sport")
    private Long idSport;

    @Column(name = "n_sport", nullable = false, length = 255)
    private String nameSport;

    @Column(name = "img_sport", length = 255)
    private String imgSport;

    @Column(name = "slug_sport", length = 255)
    private String slugSport;

    @ManyToMany(
        mappedBy = "sports",
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    private Set<CourtEntity> courts = new HashSet<>();

    // mappedBy desgina propietario de la relación
    // cascade mantiene la integridad referencial entre tablas
    @OneToMany(mappedBy = "idSport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference // Marca este lado como "referencia inversa"
    private List<ActivityEntity> activities = new ArrayList<>();

    @Builder
    public SportEntity(Long idSport, String nameSport, String imgSport, String slugSport) {
        this.idSport = idSport;
        this.nameSport = nameSport;
        this.imgSport = imgSport;
        this.slugSport = slugSport;
    }
}
