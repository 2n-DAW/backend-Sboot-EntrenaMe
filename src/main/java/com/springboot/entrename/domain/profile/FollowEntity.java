package com.springboot.entrename.domain.profile;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springboot.entrename.domain.user.UserEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA

@Getter
@Setter
@NoArgsConstructor
@Entity
@IdClass(FollowId.class)
@Table(name = "follows")
public class FollowEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_follower", referencedColumnName = "id_user", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private UserEntity idFollower;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_followed", referencedColumnName = "id_user", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private UserEntity idFollowed;

    @Builder
    public FollowEntity(UserEntity idFollower, UserEntity idFollowed) {
        this.idFollower = idFollower;
        this.idFollowed = idFollowed;
    }
}
