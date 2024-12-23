package com.springboot.entrename.domain.user;

import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.user.admin.AdminEntity;
import com.springboot.entrename.domain.user.client.ClientEntity;
import com.springboot.entrename.domain.user.instructor.InstructorEntity;
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
    private Long id_user;

    @Column(name = "img_user", length = 255)
    private String img_user;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 255)
    private String username;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "surname", nullable = false, length = 255)
    private String surname;

    @Column(name = "age")
    private Integer age;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_user", nullable = false)
    private TypeUser type_user;

    @Column(name = "is_active", columnDefinition = "TINYINT") 
    private Integer is_active;

    @Column(name = "is_deleted", columnDefinition = "TINYINT") 
    private Integer is_deleted;

    @OneToOne(
        mappedBy = "id_user",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private AdminEntity id_admin;

    @OneToOne(
        mappedBy = "id_user",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private ClientEntity id_client;

    @OneToOne(
        mappedBy = "id_user",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private InstructorEntity id_instructor;

    public enum TypeUser {
        admin, client, instructor
    }

    // mappedBy desgina propietario de la relación
    // cascade mantiene la integridad referencial entre tablas
    @OneToMany(mappedBy = "idUserInstructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference // Marca este lado como "referencia inversa"
    private List<ActivityEntity> activities = new ArrayList<>();

    @Builder
    public UserEntity(
        Long id_user,
        String img_user,
        String email,
        String username,
        String name,
        String surname,
        Integer age,
        String bio,
        String password,
        TypeUser type_user,
        Integer is_active,
        Integer is_deleted
    ){
        this.id_user = id_user;
        this.img_user = img_user;
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.bio = bio;
        this.password = password;
        this.type_user = type_user;
        this.is_active = is_active;
        this.is_deleted = is_deleted;
    }
}
