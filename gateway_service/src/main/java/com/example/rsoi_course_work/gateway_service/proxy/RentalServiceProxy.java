package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.rental.CanceledRentalResponse;
import com.example.rsoi_course_work.gateway_service.model.rental.FinishedRentalResponse;
import com.example.rsoi_course_work.gateway_service.model.rental.Rental;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "rental-service", url = "https://rental-service-xelagurd.herokuapp.com/api/v1")
public interface RentalServiceProxy {

    @GetMapping("/rentals/user/{userUid}")
    public ResponseEntity<List<Rental>> getUserRentals(@PathVariable("userUid") UUID userUid);

    @GetMapping("/rentals/located-scooter/{locatedScooterUid}")
    public ResponseEntity<List<Rental>> getLocatedScooterRentals(@PathVariable("locatedScooterUid") UUID locatedScooterUid);

    @GetMapping("/rentals/{rentalUid}/user/{userUid}")
    public ResponseEntity<Rental> getUserRental(@PathVariable("userUid") UUID userUid,
                                                @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rentals/{rentalUid}/user/{userUid}/cancel")
    public ResponseEntity<CanceledRentalResponse> cancelUserRental(@PathVariable("userUid") UUID userUid,
                                                                   @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rentals/{rentalUid}/user/{userUid}/finish")
    public ResponseEntity<FinishedRentalResponse> finishUserRental(@PathVariable("userUid") UUID userUid,
                                                                   @PathVariable("rentalUid") UUID rentalUid);

    @PostMapping("/rentals")
    public ResponseEntity<HttpStatus> createRental(@RequestBody Rental rental);
}
