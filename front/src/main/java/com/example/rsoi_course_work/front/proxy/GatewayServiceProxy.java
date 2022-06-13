package com.example.rsoi_course_work.front.proxy;

import com.example.rsoi_course_work.front.model.located_scooter.CreateLocatedScooterRequest;
import com.example.rsoi_course_work.front.model.located_scooter.LocatedScooterInfo;
import com.example.rsoi_course_work.front.model.located_scooter.PaginationResponse;
import com.example.rsoi_course_work.front.model.rental.CreateRentalRequest;
import com.example.rsoi_course_work.front.model.rental.RentalInfo;
import com.example.rsoi_course_work.front.model.rental_station.CreateRentalStationRequest;
import com.example.rsoi_course_work.front.model.scooter.CreateScooterRequest;
import com.example.rsoi_course_work.front.model.statistic_operation.StatisticOperation;
import com.example.rsoi_course_work.front.model.statistic_operation.StatisticOperationInfo;
import com.example.rsoi_course_work.front.model.user.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "gateway-service", url = "http://localhost:8080/api/v1")
public interface GatewayServiceProxy {
    @GetMapping("/user")
    public ResponseEntity<UserInfo> getCurrentUser(@RequestHeader("Authorization") String jwt);

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyJwtToken(@RequestHeader("Authorization") String jwt);

    @GetMapping("/statistic-operations")
    public ResponseEntity<List<StatisticOperationInfo>> getStatisticOperations(@RequestHeader("Authorization") String jwt);

    @PostMapping("/statistic-operations")
    public ResponseEntity<HttpStatus> createStatisticOperation(@RequestHeader("Authorization") String jwt,
                                                               @RequestBody StatisticOperation statisticOperation);

    @PostMapping("/scooters")
    public ResponseEntity<HttpStatus> createScooter(@RequestHeader("Authorization") String jwt,
                                                    @RequestBody CreateScooterRequest createScooterRequest);

    @DeleteMapping("/scooters/{scooterUid}")
    public ResponseEntity<HttpStatus> removeScooter(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable("scooterUid") UUID scooterUid);

    @PostMapping("/rental-stations")
    public ResponseEntity<HttpStatus> createRentalStation(@RequestHeader("Authorization") String jwt,
                                                          @RequestBody CreateRentalStationRequest createRentalStationRequest);

    @DeleteMapping("/rental-stations/{rentalStationUid}")
    public ResponseEntity<HttpStatus> removeRentalStation(@RequestHeader("Authorization") String jwt,
                                                          @PathVariable("rentalStationUid") UUID rentalStationUid);

    @PostMapping("/located-scooters")
    public ResponseEntity<HttpStatus> createLocatedScooter(@RequestHeader("Authorization") String jwt,
                                                           @RequestBody CreateLocatedScooterRequest createLocatedScooterRequest);

    @DeleteMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<HttpStatus> removeLocatedScooter(@RequestHeader("Authorization") String jwt,
                                                           @PathVariable("locatedScooterUid") UUID locatedScooterUid);

    @GetMapping("/located-scooters/{locatedScooterUid}")
    public ResponseEntity<LocatedScooterInfo> getLocatedScooter(@RequestHeader("Authorization") String jwt,
                                                                @PathVariable("locatedScooterUid") UUID locatedScooterUid);

    @GetMapping("/located-scooters")
    public ResponseEntity<PaginationResponse> getLocatedScooters(@RequestHeader("Authorization") String jwt,
                                                                 @RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size,
                                                                 @RequestParam(required = false) Boolean showAll);

    @GetMapping("/rentals")
    public ResponseEntity<List<RentalInfo>> getUserRentals(@RequestHeader("Authorization") String jwt);

    @GetMapping("/rentals/{rentalUid}")
    public ResponseEntity<RentalInfo> getUserRental(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rentals/{rentalUid}")
    public ResponseEntity<HttpStatus> cancelUserRentalAndPaymentAndUnReserveScooter(@RequestHeader("Authorization") String jwt,
                                                                                    @PathVariable("rentalUid") UUID rentalUid);

    @PostMapping("/rentals/{rentalUid}/finish")
    public ResponseEntity<HttpStatus> finishUserRentalAndUnReserveScooter(@RequestHeader("Authorization") String jwt,
                                                                          @PathVariable("rentalUid") UUID rentalUid);

    @PostMapping("/rentals")
    public ResponseEntity<RentalInfo> reserveUserScooterAndCreateRentalAndPayment(@RequestHeader("Authorization") String jwt,
                                                                                  @RequestBody CreateRentalRequest createRentalRequest);
}
