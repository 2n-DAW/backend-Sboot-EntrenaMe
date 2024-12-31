package com.springboot.entrename.domain.inscription;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.activity.ActivityEntity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inscriptions")
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_inscription")
public class InscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscription")
    private Long id_inscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private ActivityEntity idActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_client", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private UserEntity idUserClient;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "state", columnDefinition = "TINYINT") 
    private Integer state;

    @Column(name = "slug_inscription", length = 255)
    private String slugInscription;

    @Builder
    public InscriptionEntity(
        Long id_inscription,
        ActivityEntity idActivity,
        UserEntity idUserClient,
        Date date,
        Integer state,
        String slugInscription
    ) {
        this.id_inscription = id_inscription;
        this.idActivity = idActivity;
        this.idUserClient = idUserClient;
        this.date = date;
        this.state = state;
        this.slugInscription = slugInscription;
    }
}
