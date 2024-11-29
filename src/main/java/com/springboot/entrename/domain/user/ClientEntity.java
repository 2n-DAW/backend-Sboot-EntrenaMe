package com.springboot.entrename.domain.user;

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
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UserEntity idUser;

    @Column(name = "nif", length = 255)
    private String nif;

    @Column(name = "tlf", length = 20)
    private String tlf;
    
    @Builder
    public ClientEntity(Long idClient, UserEntity idUser, String nif, String tlf) {
        this.idClient = idClient;
        this.idUser = idUser;
        this.nif = nif;
        this.tlf = tlf;
    }
}
