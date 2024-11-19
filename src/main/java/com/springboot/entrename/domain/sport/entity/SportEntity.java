package com.springboot.entrename.domain.sport.entity;

import com.springboot.entrename.domain.court.entity.CourtEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

// Lombok es una librería de Java que ayuda a reducir el código repetitivo
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sports")
public class SportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sport")
    private Long idSport;

    @Column(name = "n_sport", nullable = false, length = 255)
    private String nSport;

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
    @JsonBackReference // Marca este lado como "referencia inversa"
    private Set<CourtEntity> courts = new HashSet<>();

    @Builder
    public SportEntity(Long idSport, String nSport, String imgSport, String slugSport) {
        this.idSport = idSport;
        this.nSport = nSport;
        this.imgSport = imgSport;
        this.slugSport = slugSport;
    }
}
