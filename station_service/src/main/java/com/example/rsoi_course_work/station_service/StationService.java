package com.example.rsoi_course_work.station_service;

import com.example.rsoi_course_work.station_service.exception.ErrorResponse;
import com.example.rsoi_course_work.station_service.model.LocatedScooter;
import com.example.rsoi_course_work.station_service.model.LocatedScooterPartialList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class StationService {
    private final LocatedScooterRepository locatedScooterRepository;
    private final RentalStationRepository rentalStationRepository;

    public StationService(LocatedScooterRepository locatedScooterRepository, RentalStationRepository rentalStationRepository) {
        this.locatedScooterRepository = locatedScooterRepository;
        this.rentalStationRepository = rentalStationRepository;
    }

    public ResponseEntity<LocatedScooter> getLocatedScooter(UUID locatedScooterUid) {
        LocatedScooter locatedScooter = locatedScooterRepository.findByLocatedScooter_uid(locatedScooterUid).orElseThrow(() ->
                new ErrorResponse("Not found locatedScooter for UID"));

        return new ResponseEntity<>(locatedScooter, HttpStatus.OK);
    }

    public ResponseEntity<LocatedScooterPartialList> getLocatedScooters(Integer page, Integer size, Boolean showAll) {
        ArrayList<LocatedScooter> locatedScooters = new ArrayList<>(locatedScooterRepository.findAll());
        ArrayList<LocatedScooter> availableLocatedScooters;

        if (showAll == null || showAll) {
            availableLocatedScooters = locatedScooters;
        } else {
            availableLocatedScooters = new ArrayList<>();
            for (LocatedScooter locatedScooter : locatedScooters) {
                if (locatedScooter.getAvailability())
                    availableLocatedScooters.add(locatedScooter);
            }
        }

        int sizeLocatedScooters = size == null ? 100 : size;
        int pageLocatedScooters = page == null ? 1 : page;

        ArrayList<LocatedScooter> chosenLocatedScooters = new ArrayList<>();
        for (int i = 0; i < sizeLocatedScooters && (pageLocatedScooters - 1) * sizeLocatedScooters + i < availableLocatedScooters.size(); i++) {
            chosenLocatedScooters.add(availableLocatedScooters.get(i));
        }

        return new ResponseEntity<>(new LocatedScooterPartialList(availableLocatedScooters.size(), chosenLocatedScooters), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateLocatedScooterReserve(UUID locatedScooterUid, Boolean availability) {
        LocatedScooter locatedScooter = locatedScooterRepository.findByLocatedScooter_uid(locatedScooterUid).orElseThrow(() ->
                new ErrorResponse("Not found locatedScooter for UID"));

        locatedScooter.setAvailability(availability);
        locatedScooterRepository.save(locatedScooter);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}