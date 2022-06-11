package com.example.rsoi_course_work.scooter_service;

import com.example.rsoi_course_work.scooter_service.model.Scooter;
import com.example.rsoi_course_work.scooter_service.model.ScooterPartialList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class ScooterController {
    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @GetMapping("/scooters/{scooterUid}")
    public ResponseEntity<Scooter> getScooter(@PathVariable("scooterUid") UUID scooterUid) {
        return scooterService.getScooter(scooterUid);
    }

    @GetMapping("/scooters")
    public ResponseEntity<ScooterPartialList> getScooters(@RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer size,
                                                          @RequestParam(required = false) Boolean showAll) {
        return scooterService.getScooters(page, size, showAll);
    }

    @PatchMapping("/scooters/{scooterUid}")
    public ResponseEntity<HttpStatus> updateScooterReserve(@PathVariable("scooterUid") UUID scooterUid,
                                                       @RequestParam Boolean availability) {
        return scooterService.updateScooterReserve(scooterUid, availability);
    }
}
