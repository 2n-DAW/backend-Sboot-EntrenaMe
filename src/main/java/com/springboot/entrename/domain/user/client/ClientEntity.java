package com.springboot.entrename.domain.user.client;

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
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long id_client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UserEntity id_user;

    @Column(name = "nif", length = 255)
    private String nif;

    @Column(name = "tlf", length = 20)
    private String tlf;
    
    @Builder
    public ClientEntity(Long id_client, UserEntity id_user, String nif, String tlf) {
        this.id_client = id_client;
        this.id_user = id_user;
        this.nif = nif;
        this.tlf = tlf;
    }
}
