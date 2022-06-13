package com.example.rsoi_course_work.gateway_service;

import com.example.rsoi_course_work.gateway_service.model.located_scooter.CreateLocatedScooterRequest;
import com.example.rsoi_course_work.gateway_service.model.located_scooter.PaginationResponse;
import com.example.rsoi_course_work.gateway_service.model.rental.CreateRentalRequest;
import com.example.rsoi_course_work.gateway_service.model.rental.RentalInfo;
import com.example.rsoi_course_work.gateway_service.model.rental_station.CreateRentalStationRequest;
import com.example.rsoi_course_work.gateway_service.model.scooter.CreateScooterRequest;
import com.example.rsoi_course_work.gateway_service.model.statistic_operation.StatisticOperationInfo;
import com.example.rsoi_course_work.gateway_service.model.user.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class GatewayController {
    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfo> getCurrentUser(@RequestHeader("Authorization") String jwt) {
        return gatewayService.getCurrentUser(jwt);
    }

    @GetMapping("/statistic-operations")
    public ResponseEntity<List<StatisticOperationInfo>> getStatisticOperations(@RequestHeader("Authorization") String jwt) {
        return gatewayService.getStatisticOperations(jwt);
    }

    @PostMapping("/scooters")
    public ResponseEntity<HttpStatus> createScooter(@RequestHeader("Authorization") String jwt,
                                                    @RequestBody CreateScooterRequest createScooterRequest) {
        return gatewayService.createScooter(jwt, createScooterRequest);
    }

    @DeleteMapping("/scooters/{scooterUid}")
    public ResponseEntity<HttpStatus> removeScooter(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable("scooterUid") UUID scooterUid) {
        return gatewayService.removeScooter(jwt, scooterUid);
    }

    @PostMapping("/rental-stations")
    public ResponseEntity<HttpStatus> createRentalStation(@RequestHeader("Authorization") String jwt,
                                                          @RequestBody CreateRentalStationRequest createRentalStationRequest) {
        return gatewayService.createRentalStation(jwt, createRentalStationRequest);
    }

    @DeleteMapping("/rental-stations/{rentalStationUid}")
    public ResponseEntity<HttpStatus> removeRentalStation(@RequestHeader("Authorization") String jwt,
                                                          @PathVariable("rentalStationUid") UUID rentalStationUid) {
        return gatewayService.removeRentalStation(jwt, rentalStationUid);
    }

    @PostMapping("/located-scooters")
    public ResponseEntity<HttpStatus> createLocatedScooter(@RequestHeader("Authorization") String jwt,
                                                           @RequestBody CreateLocatedScooterRequest createLocatedScooterRequest) {
        return gatewayService.createLocatedScooter(jwt, createLocatedScooterRequest);
    }

    @DeleteMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<HttpStatus> removeLocatedScooter(@RequestHeader("Authorization") String jwt,
                                                           @PathVariable("locatedScooterUid") UUID locatedScooterUid) {
        return gatewayService.removeLocatedScooter(jwt, locatedScooterUid);
    }

    @GetMapping("/located-scooters")
    public ResponseEntity<PaginationResponse> getLocatedScooters(@RequestHeader("Authorization") String jwt,
                                                                 @RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size,
                                                                 @RequestParam(required = false) Boolean showAll) {
        return gatewayService.getLocatedScooters(jwt, page, size, showAll);
    }

    @GetMapping("/rentals")
    public ResponseEntity<List<RentalInfo>> getUserRentals(@RequestHeader("Authorization") String jwt) {
        return gatewayService.getUserRentals(jwt);
    }

    @GetMapping("/rentals/{rentalUid}")
    public ResponseEntity<RentalInfo> getUserRental(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable("rentalUid") UUID rentalUid) {
        return gatewayService.getUserRental(jwt, rentalUid);
    }

    @DeleteMapping("/rentals/{rentalUid}")
    public ResponseEntity<HttpStatus> cancelUserRentalAndPaymentAndUnReserveScooter(@RequestHeader("Authorization") String jwt,
                                                                                    @PathVariable("rentalUid") UUID rentalUid) {
        return gatewayService.cancelUserRentalAndPaymentAndUnReserveScooter(jwt, rentalUid);
    }

    @PostMapping("/rentals/{rentalUid}/finish")
    public ResponseEntity<HttpStatus> finishUserRentalAndUnReserveScooter(@RequestHeader("Authorization") String jwt,
                                                                          @PathVariable("rentalUid") UUID rentalUid) {
        return gatewayService.finishUserRentalAndUnReserveScooter(jwt, rentalUid);
    }

    @PostMapping("/rentals")
    public ResponseEntity<RentalInfo> reserveUserScooterAndCreateRentalAndPayment(@RequestHeader("Authorization") String jwt,
                                                                                  @RequestBody CreateRentalRequest createRentalRequest) {
        return gatewayService.reserveUserScooterAndCreateRentalAndPayment(jwt, createRentalRequest);
    }
}
