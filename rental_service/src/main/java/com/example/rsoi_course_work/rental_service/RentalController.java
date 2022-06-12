package com.example.rsoi_course_work.rental_service;

import com.example.rsoi_course_work.rental_service.model.CanceledRentalResponse;
import com.example.rsoi_course_work.rental_service.model.FinishedRentalResponse;
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

    @GetMapping("/rentals/user/{userUid}")
    public ResponseEntity<List<Rental>> getUserRentals(@PathVariable("userUid") UUID userUid) {
        return rentalService.getUserRentals(userUid);
    }

    @GetMapping("/rentals/located-scooter/{locatedScooterUid}")
    public ResponseEntity<List<Rental>> getLocatedScooterRentals(@PathVariable("locatedScooterUid") UUID locatedScooterUid) {
        return rentalService.getLocatedScooterRentals(locatedScooterUid);
    }

    @GetMapping("/rentals/{rentalUid}/user/{userUid}")
    public ResponseEntity<Rental> getUserRental(@PathVariable("userUid") UUID userUid,
                                                @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.getUserRental(userUid, rentalUid);
    }

    @DeleteMapping("/rentals/{rentalUid}/user/{userUid}/cancel")
    public ResponseEntity<CanceledRentalResponse> cancelUserRental(@PathVariable("userUid") UUID userUid,
                                                                   @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.cancelUserRental(userUid, rentalUid);
    }

    @DeleteMapping("/rentals/{rentalUid}/user/{userUid}/finish")
    public ResponseEntity<FinishedRentalResponse> finishUserRental(@PathVariable("userUid") UUID userUid,
                                                                   @PathVariable("rentalUid") UUID rentalUid){
        return rentalService.finishUserRental(userUid, rentalUid);
    }

    @PostMapping("/rentals")
    public ResponseEntity<HttpStatus> createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }
}
