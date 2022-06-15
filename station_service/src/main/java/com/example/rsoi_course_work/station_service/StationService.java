package com.example.rsoi_course_work.station_service;

import com.example.rsoi_course_work.station_service.exception.ErrorResponse;
import com.example.rsoi_course_work.station_service.model.LocatedScooter;
import com.example.rsoi_course_work.station_service.model.LocatedScooterPartialList;
import com.example.rsoi_course_work.station_service.model.RentalStation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
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
        LocatedScooter locatedScooter = locatedScooterRepository.findByLocated_scooter_uid(locatedScooterUid).orElseThrow(() ->
                new ErrorResponse("Not found located scooter for UID"));

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
        LocatedScooter locatedScooter = locatedScooterRepository.findByLocated_scooter_uid(locatedScooterUid).orElseThrow(() ->
                new ErrorResponse("Not found located scooter for UID"));

        locatedScooter.setAvailability(availability);
        locatedScooterRepository.save(locatedScooter);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateLocatedScooterRentalStation(UUID locatedScooterUid,
                                                                        UUID rentalStationUid) {
        LocatedScooter locatedScooter = locatedScooterRepository.findByLocated_scooter_uid(locatedScooterUid).orElseThrow(() ->
                new ErrorResponse("Not found located scooter for UID"));

        locatedScooter.setRental_station_uid(rentalStationUid);
        locatedScooterRepository.save(locatedScooter);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateLocatedScooterCurrentCharge(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                                        @RequestParam Integer currentCharge) {
        LocatedScooter locatedScooter = locatedScooterRepository.findByLocated_scooter_uid(locatedScooterUid).orElseThrow(() ->
                new ErrorResponse("Not found located scooter for UID"));

        locatedScooter.setCurrent_charge(currentCharge);
        locatedScooterRepository.save(locatedScooter);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<RentalStation>> getRentalStations() {
        return new ResponseEntity<>(rentalStationRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<RentalStation> getRentalStation(UUID rentalStationUid) {
        RentalStation rentalStation = rentalStationRepository.findByRental_station_uid(rentalStationUid).orElseThrow(() ->
                new ErrorResponse("Not found rental station for UID"));

        return new ResponseEntity<>(rentalStation, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> createLocatedScooter(LocatedScooter locatedScooter) {
        locatedScooterRepository.save(new LocatedScooter(locatedScooter.getLocated_scooter_uid(),
                locatedScooter.getScooter_uid(), locatedScooter.getRental_station_uid(), locatedScooter.getRegistration_number(),
                locatedScooter.getCurrent_charge(), locatedScooter.getAvailability()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> createRentalStation(RentalStation rentalStation) {
        rentalStationRepository.save(new RentalStation(rentalStation.getRental_station_uid(),
                rentalStation.getLocation()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> removeLocatedScooter(UUID locatedScooterUid) {
        LocatedScooter locatedScooter = locatedScooterRepository.findByLocated_scooter_uid(locatedScooterUid).orElseThrow(() ->
                new ErrorResponse("Not found located scooter for UID"));
        locatedScooterRepository.deleteById(locatedScooter.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> removeRentalStation(UUID rentalStationUid) {
        RentalStation rentalStation = rentalStationRepository.findByRental_station_uid(rentalStationUid).orElseThrow(() ->
                new ErrorResponse("Not found rental station for UID"));
        rentalStationRepository.deleteById(rentalStation.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}