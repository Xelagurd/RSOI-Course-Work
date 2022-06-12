package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.PairOfLocatedScooterUidAndPaymentUid;
import com.example.rsoi_course_work.gateway_service.model.Rental;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "rental-service", url = "https://rental-service-xelagurd.herokuapp.com/api/v1")
public interface RentalServiceProxy {

    @GetMapping("/rentals")
    public ResponseEntity<List<Rental>> getUserRentals(@RequestParam UUID userUid);

    @GetMapping("/rentals/{rentalUid}")
    public ResponseEntity<Rental> getUserRental(@RequestParam UUID userUid, @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rentals/{rentalUid}")
    public ResponseEntity<PairOfLocatedScooterUidAndPaymentUid> cancelUserRental(@RequestParam UUID userUid,
                                                                                 @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rentals/{rentalUid}/finish")
    public ResponseEntity<UUID> finishUserRental(@RequestParam UUID userUid,
                                                 @PathVariable("rentalUid") UUID rentalUid);

    @PostMapping("/rentals")
    public ResponseEntity<HttpStatus> createRental(@RequestBody Rental rental);
}
