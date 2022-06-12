package com.example.rsoi_course_work.station_service;

import com.example.rsoi_course_work.station_service.model.RentalStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalStationRepository extends JpaRepository<RentalStation, Long> {
    @Query("select c from RentalStation c where c.rental_station_uid = ?1")
    Optional<RentalStation> findByRental_station_uid(UUID rental_station_uid);

    @Query("delete from RentalStation where rental_station_uid = ?1")
    Void deleteByRental_station_uid(UUID rental_station_uid);
}
