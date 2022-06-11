package com.example.rsoi_course_work.gateway_service;

import com.auth0.jwt.JWT;
import com.example.rsoi_course_work.gateway_service.model.CreateRentalRequest;
import com.example.rsoi_course_work.gateway_service.model.CreateRentalResponse;
import com.example.rsoi_course_work.gateway_service.model.PaginationResponse;
import com.example.rsoi_course_work.gateway_service.model.RentalResponse;
import com.example.rsoi_course_work.gateway_service.security.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class GatewayController {
    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/scooters")
    public ResponseEntity<PaginationResponse> getScooters(@RequestParam(required = false) Integer page,
                                                      @RequestParam(required = false) Integer size,
                                                      @RequestParam(required = false) Boolean showAll) {
        String bearerToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (bearerToken != null) {
            return gatewayService.getScooters(page, size, showAll);
        } else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/rental")
    public ResponseEntity<List<RentalResponse>> getUserRentals() {
        String bearerToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (bearerToken != null) {
            String username = JWT.decode(bearerToken.substring(7)).getClaims().get("preferred_username").asString();
            return gatewayService.getUserRentals(username);
        } else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/rental/{rentalUid}")
    public ResponseEntity<RentalResponse> getUserRental(@PathVariable("rentalUid") UUID rentalUid) {
        String bearerToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (bearerToken != null) {
            String username = JWT.decode(bearerToken.substring(7)).getClaims().get("preferred_username").asString();
            return gatewayService.getUserRental(username, rentalUid);
        } else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/rental/{rentalUid}")
    public ResponseEntity<HttpStatus> cancelUserRentalAndPaymentAndUnReserveScooter(@PathVariable("rentalUid") UUID rentalUid) {
        String bearerToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (bearerToken != null) {
            String username = JWT.decode(bearerToken.substring(7)).getClaims().get("preferred_username").asString();
            return gatewayService.cancelUserRentalAndPaymentAndUnReserveScooter(username, rentalUid);
        } else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/rental/{rentalUid}/finish")
    public ResponseEntity<HttpStatus> finishUserRentalAndUnReserveScooter(@PathVariable("rentalUid") UUID rentalUid) {
        String bearerToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (bearerToken != null) {
            String username = JWT.decode(bearerToken.substring(7)).getClaims().get("preferred_username").asString();
            return gatewayService.finishUserRentalAndUnReserveScooter(username, rentalUid);
        } else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/rental")
    public ResponseEntity<CreateRentalResponse> reserveUserScooterAndCreateRentalAndPayment(@RequestBody CreateRentalRequest createRentalRequest) {
        String bearerToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (bearerToken != null) {
            String username = JWT.decode(bearerToken.substring(7)).getClaims().get("preferred_username").asString();
            return gatewayService.reserveUserScooterAndCreateRentalAndPayment(username, createRentalRequest);
        } else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/authorization")
    public ResponseEntity<String> requestAuthorizationCode() {
        return gatewayService.requestAuthorizationCode();
    }

    @GetMapping("/callback")
    public ResponseEntity<TokenResponse> authorizationCodeCallback(@RequestParam String code,
                                                                   @RequestParam String state) {
        return gatewayService.authorizationCodeCallback(code, state);
    }
}
