package com.springboot.entrename.domain.user;

import com.springboot.entrename.domain.activity.ActivityEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@Table(name = "users")
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idUser")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "img_user", length = 255)
    private String imgUser;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_user", nullable = false)
    private UserType typeUser;

    @OneToOne(
        mappedBy = "idUser",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private ClientEntity idClient;

    @OneToOne(
        mappedBy = "idUser",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private InstructorEntity idInstructor;

    public enum UserType {
        admin, client, instructor;
    }

    // mappedBy desgina propietario de la relación
    // cascade mantiene la integridad referencial entre tablas
    @OneToMany(mappedBy = "idUserInstructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference // Marca este lado como "referencia inversa"
    private List<ActivityEntity> activities = new ArrayList<>();

    @Builder
    public UserEntity(Long idUser, String imgUser, String email, String username, String password, UserType typeUser) {
        this.idUser = idUser;
        this.imgUser = imgUser;
        this.email = email;
        this.username = username;
        this.password = password;
        this.typeUser = typeUser;
    }
}
