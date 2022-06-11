package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.Scooter;
import com.example.rsoi_course_work.gateway_service.model.ScooterPartialList;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.Produces;
import java.util.UUID;

@FeignClient(name = "scooter-service", url = "https://scooter-service-xelagurd.herokuapp.com/api/v1")
public interface ScooterServiceProxy {
    @GetMapping("/scooters/{scooterUid}")
    public ResponseEntity<Scooter> getScooter(@PathVariable("scooterUid") UUID scooterUid);

    @GetMapping("/scooters")
    public ResponseEntity<ScooterPartialList> getScooters(@RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size,
                                                            @RequestParam(required = false) Boolean showAll);

    @PatchMapping("/scooters/{scooterUid}")
    public ResponseEntity<HttpStatus> updateScooterReserve(@PathVariable("scooterUid") UUID scooterUid,
                                                       @RequestParam Boolean availability);
}