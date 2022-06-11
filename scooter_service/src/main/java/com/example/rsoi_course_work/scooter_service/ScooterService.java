package com.example.rsoi_course_work.scooter_service;

import com.example.rsoi_course_work.scooter_service.model.Scooter;
import com.example.rsoi_course_work.scooter_service.model.ScooterPartialList;
import com.example.rsoi_course_work.scooter_service.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ResponseEntity<ScooterPartialList> getScooters(Integer page, Integer size, Boolean showAll) {
        ArrayList<Scooter> scooters = new ArrayList<>(scooterRepository.findAll());
        ArrayList<Scooter> availableScooters;

        if (showAll == null || showAll) {
            availableScooters = scooters;
        } else {
            availableScooters = new ArrayList<>();
            for (Scooter scooter : scooters) {
                if (scooter.getAvailability())
                    availableScooters.add(scooter);
            }
        }

        int sizeScooters = size == null ? 100 : size;
        int pageScooters = page == null ? 1 : page;

        ArrayList<Scooter> chosenScooters = new ArrayList<>();
        for (int i = 0; i < sizeScooters && (pageScooters - 1) * sizeScooters + i < availableScooters.size(); i++) {
            chosenScooters.add(availableScooters.get(i));
        }

        return new ResponseEntity<>(new ScooterPartialList(availableScooters.size(), chosenScooters), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateScooterReserve(UUID scooterUid, Boolean availability) {
        Scooter scooter = scooterRepository.findByScooter_uid(scooterUid).orElseThrow(() ->
                new ErrorResponse("Not found scooter for UID"));

        scooter.setAvailability(availability);
        scooterRepository.save(scooter);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}