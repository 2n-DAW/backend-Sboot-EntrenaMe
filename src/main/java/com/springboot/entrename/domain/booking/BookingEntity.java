package com.springboot.entrename.domain.booking;

import com.springboot.entrename.domain.courtHour.CourtHourEntity;
import com.springboot.entrename.domain.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bookings")
// Identificador único. Hace que otras entidades puedan referenciar a esta y viceversa sin entrar en bucle en la serialización
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_booking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private Long id_booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonManagedReference // Marca este lado como "propietario"
    private UserEntity idUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_count_hours", referencedColumnName = "id_court_hour", nullable = false)
    private CourtHourEntity id_count_hours;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "is_deleted", columnDefinition = "TINYINT") 
    private Integer isDeleted;

    @Column(name = "slug_booking", length = 255)
    private String slugBooking;

    @Builder
    public BookingEntity(
        Long id_booking,
        UserEntity idUser,
        CourtHourEntity id_count_hours,
        Date date,
        Integer isDeleted,
        String slugBooking
    ) {
        this.id_booking = id_booking;
        this.idUser = idUser;
        this.id_count_hours = id_count_hours;
        this.date = date;
        this.isDeleted = isDeleted;
        this.slugBooking = slugBooking;
    }
}
