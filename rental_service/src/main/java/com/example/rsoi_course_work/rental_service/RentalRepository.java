package com.example.rsoi_course_work.rental_service;

import com.example.rsoi_course_work.rental_service.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("select r from Rental r where r.user_uid = ?1")
    List<Rental> findByUser_uid(UUID userUid);

    @Query("select r from Rental r where r.user_uid = ?1 and r.rental_uid = ?2")
    Optional<Rental> findByUser_uidAndRental_uid(UUID userUid, UUID rental_uid);
}