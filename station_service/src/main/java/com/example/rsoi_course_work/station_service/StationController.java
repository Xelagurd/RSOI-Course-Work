package com.example.rsoi_course_work.station_service;

import com.example.rsoi_course_work.station_service.model.LocatedScooter;
import com.example.rsoi_course_work.station_service.model.LocatedScooterPartialList;
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

    @PatchMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<HttpStatus> updateLocatedScooterReserve(@PathVariable("locatedScooterUid") UUID locatedScooterUid,
                                                       @RequestParam Boolean availability) {
        return stationService.updateLocatedScooterReserve(locatedScooterUid, availability);
    }
}
