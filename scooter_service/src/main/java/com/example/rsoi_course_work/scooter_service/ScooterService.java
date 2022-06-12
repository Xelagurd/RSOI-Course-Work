package com.example.rsoi_course_work.scooter_service;

import com.example.rsoi_course_work.scooter_service.exception.ErrorResponse;
import com.example.rsoi_course_work.scooter_service.model.Scooter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScooterService {
    private final ScooterRepository scooterRepository;

    public ScooterService(ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
    }

    public ResponseEntity<Scooter> getScooter(UUID scooterUid) {
        Scooter scooter = scooterRepository.findByScooter_uid(scooterUid).orElseThrow(() ->
                new ErrorResponse("Not found scooter for UID"));

        return new ResponseEntity<>(scooter, HttpStatus.OK);
    }

    public ResponseEntity<List<Scooter>> getScooters() {
        return new ResponseEntity<>(scooterRepository.findAll(), HttpStatus.OK);
    }
}