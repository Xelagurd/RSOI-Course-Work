package com.example.rsoi_course_work.rental_service;

import com.example.rsoi_course_work.rental_service.model.PairOfScooterUidAndPaymentUid;
import com.example.rsoi_course_work.rental_service.model.Rental;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/rental")
    public ResponseEntity<List<Rental>> getUserRentals(@RequestParam String username) {
        return rentalService.getUserRentals(username);
    }

    @GetMapping("/rental/{rentalUid}")
    public ResponseEntity<Rental> getUserRental(@RequestParam String username,
                                                @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.getUserRental(username, rentalUid);
    }

    @DeleteMapping("/rental/{rentalUid}")
    public ResponseEntity<PairOfScooterUidAndPaymentUid> cancelUserRental(@RequestParam String username,
                                                                      @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.cancelUserRental(username, rentalUid);
    }

    @DeleteMapping("/rental/{rentalUid}/finish")
    public ResponseEntity<UUID> finishUserRental(@RequestParam String username,
                                                 @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.finishUserRental(username, rentalUid);
    }

    @PostMapping("/rental")
    public ResponseEntity<HttpStatus> createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }
}
