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

    public ResponseEntity<HttpStatus> createScooter(Scooter scooter) {
        scooterRepository.save(new Scooter(scooter.getScooter_uid(), scooter.getProvider(),
                scooter.getMax_speed(), scooter.getPrice(), scooter.getCharge_recovery(), scooter.getCharge_consumption()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> removeScooter(UUID scooterUid) {
        scooterRepository.deleteByScooter_uid(scooterUid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}