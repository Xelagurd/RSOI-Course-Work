package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.LocatedScooter;
import com.example.rsoi_course_work.gateway_service.model.LocatedScooterPartialList;
import com.example.rsoi_course_work.gateway_service.model.RentalStation;
import com.example.rsoi_course_work.gateway_service.model.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "station-service", url = "https://station-service-xelagurd.herokuapp.com/api/v1")
public interface StationServiceProxy {
    @GetMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<LocatedScooter> getLocatedScooter(@PathVariable("locatedScooterUid") UUID locatedScooterUid);

    @GetMapping("/located-scooters")
    public ResponseEntity<LocatedScooterPartialList> getLocatedScooters(@RequestParam(required = false) Integer page,
                                                                        @RequestParam(required = false) Integer size,
                                                                        @RequestParam(required = false) Boolean showAll);

    @PatchMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<HttpStatus> updateLocatedScooterReserve(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                                  @RequestParam Boolean availability);

    @GetMapping("/rental-stations/{rentalStationUid}")
    public ResponseEntity<RentalStation> getRentalStation(@PathVariable("rentalStationUid") UUID rentalStationUid);
}