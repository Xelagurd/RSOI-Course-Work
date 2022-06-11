package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.PairOfScooterUidAndPaymentUid;
import com.example.rsoi_course_work.gateway_service.model.Rental;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "rental-service", url = "https://rental-service-xelagurd.herokuapp.com/api/v1")
public interface RentalServiceProxy {

    @GetMapping("/rental")
    public ResponseEntity<List<Rental>> getUserRentals(@RequestParam String username);

    @GetMapping("/rental/{rentalUid}")
    public ResponseEntity<Rental> getUserRental(@RequestParam String username, @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rental/{rentalUid}")
    public ResponseEntity<PairOfScooterUidAndPaymentUid> cancelUserRental(@RequestParam String username,
                                                                      @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rental/{rentalUid}/finish")
    public ResponseEntity<UUID> finishUserRental(@RequestParam String username,
                                                 @PathVariable("rentalUid") UUID rentalUid);

    @PostMapping("/rental")
    public ResponseEntity<HttpStatus> createRental(@RequestBody Rental rental);
}
