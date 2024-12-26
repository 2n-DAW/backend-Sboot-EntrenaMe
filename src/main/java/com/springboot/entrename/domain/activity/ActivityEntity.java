package com.springboot.entrename.domain.activity;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.inscription.InscriptionEntity;
import com.springboot.entrename.domain.sport.SportEntity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "activities")
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idActivity")
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_activity")
    private Long idActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_instructor", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private UserEntity idUserInstructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sport", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private SportEntity idSport;

    @Column(name = "n_activity", nullable = false, length = 255)
    private String nameActivity;

    @Column(name = "description")
    private String description;

    @Column(name = "week_day", length = 255)
    private String weekDay;

    @Column(name = "slot_hour", length = 255)
    private String slotHour;

    @Column(name = "img_activity", length = 255)
    private String imgActivity;

    @Column(name = "spots")
    private int spots;

    @Column(name = "slug_activity", length = 255)
    private String slugActivity;

    @OneToMany(mappedBy = "id_activity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<InscriptionEntity> inscriptions = new ArrayList<>();

    @Builder
    public ActivityEntity(
        Long idActivity,
        UserEntity idUserInstructor,
        SportEntity idSport,
        String nameActivity,
        String description,
        String weekDay,
        String slotHour,
        String imgActivity,
        int spots,
        String slugActivity
    ) {
        this.idActivity = idActivity;
        this.idUserInstructor = idUserInstructor;
        this.idSport = idSport;
        this.nameActivity = nameActivity;
        this.description = description;
        this.weekDay = weekDay;
        this.slotHour = slotHour;
        this.imgActivity = imgActivity;
        this.spots = spots;
        this.slugActivity = slugActivity;
    }
}
