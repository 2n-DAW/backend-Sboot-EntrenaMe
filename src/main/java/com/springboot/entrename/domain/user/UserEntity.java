package com.springboot.entrename.domain.user;

import com.springboot.entrename.domain.activity.ActivityEntity;
import com.springboot.entrename.domain.user.admin.AdminEntity;
import com.springboot.entrename.domain.user.client.ClientEntity;
import com.springboot.entrename.domain.user.instructor.InstructorEntity;
import com.springboot.entrename.domain.booking.BookingEntity;
import com.springboot.entrename.domain.inscription.InscriptionEntity;
import com.springboot.entrename.domain.comment.CommentEntity;
import com.springboot.entrename.domain.profile.FollowEntity;
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
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idUser")
public class UserEntity {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR) // Usa VARCHAR para almacenar el UUID como texto
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private UUID idUser;

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
    private TypeUser typeUser;

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

    public void generateUUID() {
        if (this.idUser == null) {
            this.idUser = UUID.randomUUID();
        }
    }

    // mappedBy indica entidad no propietaria de la relación
    // cascade mantiene la integridad referencial entre tablas
    @OneToMany(mappedBy = "idUserInstructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference // Marca este lado como "referencia inversa"
    private List<ActivityEntity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "idUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<BookingEntity> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "idUserClient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<InscriptionEntity> inscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "idUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "idFollower", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FollowEntity> followings = new ArrayList<>();

    @OneToMany(mappedBy = "idFollowed", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FollowEntity> followers = new ArrayList<>();

    @Builder
    public UserEntity(
        UUID idUser,
        String img_user,
        String email,
        String username,
        String name,
        String surname,
        Integer age,
        String bio,
        String password,
        TypeUser typeUser,
        Integer is_active,
        Integer is_deleted
    ){
        this.idUser = idUser;
        this.img_user = img_user;
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.bio = bio;
        this.password = password;
        this.typeUser = typeUser;
        this.is_active = is_active;
        this.is_deleted = is_deleted;
    }
}
