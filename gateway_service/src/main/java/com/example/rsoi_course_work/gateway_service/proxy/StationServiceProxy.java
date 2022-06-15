package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.located_scooter.LocatedScooter;
import com.example.rsoi_course_work.gateway_service.model.located_scooter.LocatedScooterPartialList;
import com.example.rsoi_course_work.gateway_service.model.rental_station.RentalStation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "station-service", url = "http://localhost:8030/api/v1")
public interface StationServiceProxy {
    @GetMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<LocatedScooter> getLocatedScooter(@PathVariable("locatedScooterUid") UUID locatedScooterUid);

    @GetMapping("/located-scooters")
    public ResponseEntity<LocatedScooterPartialList> getLocatedScooters(@RequestParam(required = false) Integer page,
                                                                        @RequestParam(required = false) Integer size,
                                                                        @RequestParam(required = false) Boolean showAll);

    @PatchMapping("/located-scooters/{locatedScooterUid}/reserve")
    public ResponseEntity<HttpStatus> updateLocatedScooterReserve(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                                  @RequestParam Boolean availability);

    @PatchMapping("/located-scooters/{locatedScooterUid}/rental-station")
    public ResponseEntity<HttpStatus> updateLocatedScooterRentalStation(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                                        @RequestParam UUID rentalStationUid);

    @PatchMapping("/located-scooters/{locatedScooterUid}/current-charge")
    public ResponseEntity<HttpStatus> updateLocatedScooterCurrentCharge(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                                        @RequestParam Integer currentCharge);

    @GetMapping("/rental-stations")
    public ResponseEntity<List<RentalStation>> getRentalStations();

    @GetMapping("/rental-stations/{rentalStationUid}")
    public ResponseEntity<RentalStation> getRentalStation(@PathVariable("rentalStationUid") UUID rentalStationUid);

    @PostMapping("/located-scooters")
    public ResponseEntity<HttpStatus> createLocatedScooter(@RequestBody LocatedScooter locatedScooter);

    @PostMapping("/rental-stations")
    public ResponseEntity<HttpStatus> createRentalStation(@RequestBody RentalStation rentalStation);

    @DeleteMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<HttpStatus> removeLocatedScooter(@PathVariable("locatedScooterUid") UUID locatedScooterUid);

    @DeleteMapping("/rental-stations/{rentalStationUid}")
    public ResponseEntity<HttpStatus> removeRentalStation(@PathVariable("rentalStationUid") UUID rentalStationUid);
}