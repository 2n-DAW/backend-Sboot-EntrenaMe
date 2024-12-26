package com.springboot.entrename.domain.booking;

import com.springboot.entrename.domain.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long>, JpaSpecificationExecutor<BookingEntity> {
    Optional<BookingEntity> findBySlugBooking(String slug);

    List<BookingEntity> findByIdUserAndIsDeleted(UserEntity idUser, Integer isDeleted);
}
