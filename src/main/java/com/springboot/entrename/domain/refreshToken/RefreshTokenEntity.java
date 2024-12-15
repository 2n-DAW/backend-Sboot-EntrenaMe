package com.springboot.entrename.domain.refreshToken;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "refresh_tokens",
    uniqueConstraints = @UniqueConstraint(columnNames = "id_user")
)
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRefresh")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_refresh")
    private Long idRefresh;

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "refresh_token", nullable = false, length = 500)
    private String refreshToken;

    @Builder
    public RefreshTokenEntity(Long idRefresh, Long idUser, String refreshToken) {
        this.idRefresh = idRefresh;
        this.idUser = idUser;
        this.refreshToken = refreshToken;
    }
}
