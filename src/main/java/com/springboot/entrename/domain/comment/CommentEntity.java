package com.springboot.entrename.domain.comment;

import com.springboot.entrename.domain.user.UserEntity;
import com.springboot.entrename.domain.activity.ActivityEntity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*; // Contiene las clases y anotaciones necesarias para trabajar con JPA
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Long id_comment;

    @Column(name = "body", length = 255, nullable = false)
    private String body;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private UserEntity idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private ActivityEntity idActivity;

    @Column(name = "slug_comment", length = 255)
    private String slugComment;

    @Builder
    public CommentEntity(
        Long id_comment,
        String body,
        Date date,
        UserEntity idUser,
        ActivityEntity idActivity,
        String slugComment
    ) {
        this.id_comment = id_comment;
        this.body = body;
        this.date = date;
        this.idUser = idUser;
        this.idActivity = idActivity;
        this.slugComment = slugComment;
    }
}
