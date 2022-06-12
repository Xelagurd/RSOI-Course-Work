package com.example.rsoi_course_work.scooter_service;

import com.example.rsoi_course_work.scooter_service.model.Scooter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
