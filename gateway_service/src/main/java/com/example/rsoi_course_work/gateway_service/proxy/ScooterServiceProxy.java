package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.scooter.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "scooter-service", url = "https://scooter-service-xelagurd.herokuapp.com/api/v1")
public interface ScooterServiceProxy {
    @GetMapping("/scooters/{scooterUid}")
    public ResponseEntity<Scooter> getScooter(@PathVariable("scooterUid") UUID scooterUid);

    @GetMapping("/scooters")
    public ResponseEntity<List<Scooter>> getScooters();

    @PostMapping("/scooters")
    public ResponseEntity<HttpStatus> createScooter(@RequestBody Scooter scooter);

    @DeleteMapping("/scooters/{scooterUid}")
    public ResponseEntity<HttpStatus> removeScooter(@PathVariable("scooterUid") UUID scooterUid);
}