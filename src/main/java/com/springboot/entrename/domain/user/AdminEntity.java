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
@Table(name = "admins")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Long idAdmin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UserEntity idUser;
    
    @Builder
    public AdminEntity(Long idAdmin, UserEntity idUser) {
        this.idAdmin = idAdmin;
        this.idUser = idUser;
    }
}
