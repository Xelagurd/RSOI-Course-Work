package com.example.rsoi_course_work.rental_service;

import com.example.rsoi_course_work.rental_service.model.PairOfLocatedScooterUidAndPaymentUid;
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

    @GetMapping("/rentals")
    public ResponseEntity<List<Rental>> getUserRentals(@RequestParam UUID userUid) {
        return rentalService.getUserRentals(userUid);
    }

    @GetMapping("/rentals/{rentalUid}")
    public ResponseEntity<Rental> getUserRental(@RequestParam UUID userUid,
                                                @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.getUserRental(userUid, rentalUid);
    }

    @DeleteMapping("/rentals/{rentalUid}")
    public ResponseEntity<PairOfLocatedScooterUidAndPaymentUid> cancelUserRental(@RequestParam UUID userUid,
                                                                                 @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.cancelUserRental(userUid, rentalUid);
    }

    @DeleteMapping("/rentals/{rentalUid}/finish")
    public ResponseEntity<UUID> finishUserRental(@RequestParam UUID userUid,
                                                 @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.finishUserRental(userUid, rentalUid);
    }

    @PostMapping("/rentals")
    public ResponseEntity<HttpStatus> createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }
}
