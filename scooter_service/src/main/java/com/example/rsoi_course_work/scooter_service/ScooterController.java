package com.example.rsoi_course_work.scooter_service;

import com.example.rsoi_course_work.scooter_service.model.Scooter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<Scooter>> getScooters() {
        return scooterService.getScooters();
    }

    @PostMapping("/scooters")
    public ResponseEntity<HttpStatus> createScooter(@RequestBody Scooter scooter){
        return scooterService.createScooter(scooter);
    }

    @DeleteMapping("/scooters/{scooterUid}")
    public ResponseEntity<HttpStatus> removeScooter(@PathVariable("scooterUid") UUID scooterUid){
        return scooterService.removeScooter(scooterUid);
    }
}
