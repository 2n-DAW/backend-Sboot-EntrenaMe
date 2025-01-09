package com.springboot.entrename.domain.user.instructor;

// Lombok es una librería de Java que ayuda a reducir el código repetitivo
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import com.springboot.entrename.domain.user.UserEntity;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "instructors")
public class InstructorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instructor")
    private Long id_instructor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UserEntity id_user;

    @Column(name = "nif", length = 255)
    private String nif;

    @Column(name = "tlf", length = 20)
    private String tlf;

    @Column(name = "address", length = 255)
    private String address;
    
    @Builder
    public InstructorEntity(Long id_instructor, UserEntity id_user, String nif, String tlf, String address) {
        this.id_instructor = id_instructor;
        this.id_user = id_user;
        this.nif = nif;
        this.tlf = tlf;
        this.address = address;
    }
}
