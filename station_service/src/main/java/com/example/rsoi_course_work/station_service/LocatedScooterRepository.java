package com.example.rsoi_course_work.station_service;

import com.example.rsoi_course_work.station_service.model.LocatedScooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocatedScooterRepository extends JpaRepository<LocatedScooter, Long> {
    @Query("select c from LocatedScooter c where c.located_scooter_uid = ?1")
    Optional<LocatedScooter> findByLocatedScooter_uid(UUID located_scooter_uid);

    @Query("delete from LocatedScooter where located_scooter_uid = ?1")
    Void deleteByLocated_scooter_uid(UUID located_scooter_uid);
}