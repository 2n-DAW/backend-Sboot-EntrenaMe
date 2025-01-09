package com.springboot.entrename.domain.month;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.springboot.entrename.domain.courtHour.CourtHourEntity;

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
@Table(name = "months")
public class MonthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_month")
    private Long idMonth;

    @Column(name = "n_month", nullable = false, length = 255)
    private String nameMonth;

    @Column(name = "slug_month", length = 255)
    private String slugMonth;

    @OneToMany(
        mappedBy = "id_month",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JsonBackReference // Marca este lado como "referencia inversa"
    private Set<CourtHourEntity> courtsHours = new HashSet<>();
    // private List<CourtHourEntity> courtsHours = new ArrayList<>();

    @Builder
    public MonthEntity(Long idMonth, String nameMonth, String slugMonth) {
        this.idMonth = idMonth;
        this.nameMonth = nameMonth;
        this.slugMonth = slugMonth;
    }
}
