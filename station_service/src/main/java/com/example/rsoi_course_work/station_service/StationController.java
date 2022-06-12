package com.example.rsoi_course_work.station_service;

import com.example.rsoi_course_work.station_service.model.LocatedScooter;
import com.example.rsoi_course_work.station_service.model.LocatedScooterPartialList;
import com.example.rsoi_course_work.station_service.model.RentalStation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<LocatedScooter> getLocatedScooter(@PathVariable("locatedScooterUid") UUID locatedScooterUid) {
        return stationService.getLocatedScooter(locatedScooterUid);
    }

    @GetMapping("/located-scooters")
    public ResponseEntity<LocatedScooterPartialList> getLocatedScooters(@RequestParam(required = false) Integer page,
                                                                        @RequestParam(required = false) Integer size,
                                                                        @RequestParam(required = false) Boolean showAll) {
        return stationService.getLocatedScooters(page, size, showAll);
    }

    @PatchMapping("/located-scooters/{locatedScooterUid}/reserve")
    public ResponseEntity<HttpStatus> updateLocatedScooterReserve(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                                  @RequestParam Boolean availability) {
        return stationService.updateLocatedScooterReserve(locatedScooterUid, availability);
    }

    @PatchMapping("/located-scooters/{locatedScooterUid}/rental-station")
    public ResponseEntity<HttpStatus> updateLocatedScooterRentalStation(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                                        @RequestParam UUID rentalStationUid) {
        return stationService.updateLocatedScooterRentalStation(locatedScooterUid, rentalStationUid);
    }

    @PatchMapping("/located-scooters/{locatedScooterUid}/current-charge")
    public ResponseEntity<HttpStatus> updateLocatedScooterCurrentCharge(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                                        @RequestParam Integer currentCharge) {
        return stationService.updateLocatedScooterCurrentCharge(locatedScooterUid, currentCharge);
    }

    @GetMapping("/rental-stations/{rentalStationUid}")
    public ResponseEntity<RentalStation> getRentalStation(@PathVariable("rentalStationUid") UUID rentalStationUid) {
        return stationService.getRentalStation(rentalStationUid);
    }

    @PostMapping("/located-scooters")
    public ResponseEntity<HttpStatus> createLocatedScooter(@RequestBody LocatedScooter locatedScooter) {
        return stationService.createLocatedScooter(locatedScooter);
    }

    @PostMapping("/rental-stations")
    public ResponseEntity<HttpStatus> createRentalStation(@RequestBody RentalStation rentalStation) {
        return stationService.createRentalStation(rentalStation);
    }

    @DeleteMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<HttpStatus> removeLocatedScooter(@PathVariable("locatedScooterUid") UUID locatedScooterUid) {
        return stationService.removeLocatedScooter(locatedScooterUid);
    }

    @DeleteMapping("/rental-stations/{rentalStationUid}")
    public ResponseEntity<HttpStatus> removeRentalStation(@PathVariable("rentalStationUid") UUID rentalStationUid) {
        return stationService.removeRentalStation(rentalStationUid);
    }
}
