/*
package com.example.rsoi_course_work.front;

import com.example.rsoi_course_work.front.model.located_scooter.CreateLocatedScooterRequest;
import com.example.rsoi_course_work.front.model.located_scooter.PaginationResponse;
import com.example.rsoi_course_work.front.model.rental.CreateRentalRequest;
import com.example.rsoi_course_work.front.model.rental.RentalInfo;
import com.example.rsoi_course_work.front.model.rental_station.CreateRentalStationRequest;
import com.example.rsoi_course_work.front.model.scooter.CreateScooterRequest;
import com.example.rsoi_course_work.front.model.statistic_operation.StatisticOperationInfo;
import com.example.rsoi_course_work.front.model.user.UserInfo;
import com.example.rsoi_course_work.front.model.user.security.RegistrationRequest;
import com.example.rsoi_course_work.front.model.user.security.SessionJwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class FrontController {
    private final FrontService frontService;

    public FrontController(FrontService frontService) {
        this.frontService = frontService;
    }

    @GetMapping("/auth")
    public ResponseEntity<SessionJwtResponse> userAuthorization(@RequestParam("login") String login, @RequestParam("password") String password) {
        return frontService.userAuthorization(login, password);
    }

    @PostMapping("/registration")
    public ResponseEntity<SessionJwtResponse> userRegistration(@RequestBody RegistrationRequest registrationRequest) {
        return frontService.userRegistration(registrationRequest);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfo> getCurrentUser() {
        return frontService.getCurrentUser();
    }

    @GetMapping("/statistic-operations")
    public ResponseEntity<List<StatisticOperationInfo>> getStatisticOperations() {
        return frontService.getStatisticOperations();
    }

    @PostMapping("/scooters")
    public ResponseEntity<HttpStatus> createScooter(@RequestBody CreateScooterRequest createScooterRequest) {
        return frontService.createScooter(createScooterRequest);
    }

    @DeleteMapping("/scooters/{scooterUid}")
    public ResponseEntity<HttpStatus> removeScooter(@PathVariable("scooterUid") UUID scooterUid) {
        return frontService.removeScooter(scooterUid);
    }

    @PostMapping("/rental-stations")
    public ResponseEntity<HttpStatus> createRentalStation(@RequestBody CreateRentalStationRequest createRentalStationRequest) {
        return frontService.createRentalStation(createRentalStationRequest);
    }

    @DeleteMapping("/rental-stations/{rentalStationUid}")
    public ResponseEntity<HttpStatus> removeRentalStation(@PathVariable("rentalStationUid") UUID rentalStationUid) {
        return frontService.removeRentalStation(rentalStationUid);
    }

    @PostMapping("/located-scooters")
    public ResponseEntity<HttpStatus> createLocatedScooter(@RequestBody CreateLocatedScooterRequest createLocatedScooterRequest) {
        return frontService.createLocatedScooter(createLocatedScooterRequest);
    }

    @DeleteMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<HttpStatus> removeLocatedScooter(@PathVariable("locatedScooterUid") UUID locatedScooterUid) {
        return frontService.removeLocatedScooter(locatedScooterUid);
    }

    @GetMapping("/located-scooters")
    public ResponseEntity<PaginationResponse> getLocatedScooters(@RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size,
                                                                 @RequestParam(required = false) Boolean showAll) {
        return frontService.getLocatedScooters(page, size, showAll);
    }

    @GetMapping("/rentals")
    public ResponseEntity<List<RentalInfo>> getUserRentals() {
        return frontService.getUserRentals();
    }

    @GetMapping("/rentals/{rentalUid}")
    public ResponseEntity<RentalInfo> getUserRental(@PathVariable("rentalUid") UUID rentalUid) {
        return frontService.getUserRental(rentalUid);
    }

    @DeleteMapping("/rentals/{rentalUid}")
    public ResponseEntity<HttpStatus> cancelUserRentalAndPaymentAndUnReserveScooter(@PathVariable("rentalUid") UUID rentalUid) {
        return frontService.cancelUserRentalAndPaymentAndUnReserveScooter(rentalUid);
    }

    @PostMapping("/rentals/{rentalUid}/finish")
    public ResponseEntity<HttpStatus> finishUserRentalAndUnReserveScooter(@PathVariable("rentalUid") UUID rentalUid) {
        return frontService.finishUserRentalAndUnReserveScooter(rentalUid);
    }

    @PostMapping("/rentals")
    public ResponseEntity<RentalInfo> reserveUserScooterAndCreateRentalAndPayment(@RequestBody CreateRentalRequest createRentalRequest) {
        return frontService.reserveUserScooterAndCreateRentalAndPayment(createRentalRequest);
    }
}
*/
