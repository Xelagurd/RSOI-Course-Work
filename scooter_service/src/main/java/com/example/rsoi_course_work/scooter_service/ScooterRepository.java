package com.example.rsoi_course_work.scooter_service;

import com.example.rsoi_course_work.scooter_service.model.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, Long> {
    @Query("select c from Scooter c where c.scooter_uid = ?1")
    Optional<Scooter> findByScooter_uid(UUID scooter_uid);
}