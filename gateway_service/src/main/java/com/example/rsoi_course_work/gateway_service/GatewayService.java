package com.example.rsoi_course_work.gateway_service;

import com.example.rsoi_course_work.gateway_service.exception.FeignRetriesException;
import com.example.rsoi_course_work.gateway_service.exception.ValidationErrorResponse;
import com.example.rsoi_course_work.gateway_service.model.*;
import com.example.rsoi_course_work.gateway_service.proxy.AuthenticationProxy;
import com.example.rsoi_course_work.gateway_service.proxy.ScooterServiceProxy;
import com.example.rsoi_course_work.gateway_service.proxy.PaymentServiceProxy;
import com.example.rsoi_course_work.gateway_service.proxy.RentalServiceProxy;
import com.example.rsoi_course_work.gateway_service.security.SessionRequest;
import com.example.rsoi_course_work.gateway_service.security.SessionResponse;
import com.example.rsoi_course_work.gateway_service.security.TokenResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class GatewayService {
    private final ScooterServiceProxy scooterServiceProxy;
    private final RentalServiceProxy rentalServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;
    private final AuthenticationProxy authenticationProxy;
    private final QueueService queueService;

    @Value("${okta.oauth2.client-id}")
    private String clientId;
    @Value("${okta.oauth2.client-secret}")
    private String clientSecret;
    private final String callbackUri = "https://gateway-service-xelagurd.herokuapp.com/api/v1/callback";

    public GatewayService(ScooterServiceProxy scooterServiceProxy, RentalServiceProxy rentalServiceProxy,
                          PaymentServiceProxy paymentServiceProxy, AuthenticationProxy authenticationProxy,
                          QueueService queueService) {
        this.scooterServiceProxy = scooterServiceProxy;
        this.rentalServiceProxy = rentalServiceProxy;
        this.paymentServiceProxy = paymentServiceProxy;
        this.authenticationProxy = authenticationProxy;
        this.queueService = queueService;
    }

    public ResponseEntity<PaginationResponse> getScooters(Integer page, Integer size, Boolean showAll) {
        ResponseEntity<ScooterPartialList> answer;
        try {
            answer = scooterServiceProxy.getScooters(page, size, showAll);
        } catch (FeignException e) {
            throw new FeignRetriesException("Scooter Service unavailable");
        }

        List<ScooterResponse> scooterResponses = new ArrayList<>();
        for (Scooter scooter : answer.getBody().getScooters()) {
            scooterResponses.add(new ScooterResponse(scooter.getScooter_uid(), scooter.getBrand(), scooter.getModel(),
                    scooter.getRegistration_number(), scooter.getPower(), scooter.getPrice(), scooter.getType(), scooter.getAvailability()));
        }

        return new ResponseEntity<>(new PaginationResponse(page, size, answer.getBody().getTotalElements(), scooterResponses), answer.getStatusCode());
    }

    public ResponseEntity<List<RentalResponse>> getUserRentals(String username) {
        ResponseEntity<List<Rental>> rentals;
        try {
            rentals = rentalServiceProxy.getUserRentals(username);
        } catch (FeignException e) {
            throw new FeignRetriesException("Rental Service unavailable");
        }

        List<RentalResponse> rentalResponses = new ArrayList<>();
        for (Rental rental : rentals.getBody()) {
            Scooter scooter;
            try {
                scooter = scooterServiceProxy.getScooter(rental.getScooter_uid()).getBody();
            } catch (FeignException e) {
                scooter = null;
            }

            Payment payment;
            try {
                payment = paymentServiceProxy.getPayment(rental.getPayment_uid()).getBody();
            } catch (FeignException e) {
                payment = null;
            }

            if (payment == null && scooter == null) {
                rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                        rental.getStatus(), null, null));
            } else if (payment == null) {
                rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                        rental.getStatus(), new ScooterInfo(rental.getScooter_uid(), scooter.getBrand(), scooter.getModel(), scooter.getRegistration_number()), null));
            } else if (scooter == null) {
                rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                        rental.getStatus(), null, new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())));
            } else {
                rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                        rental.getStatus(), new ScooterInfo(rental.getScooter_uid(), scooter.getBrand(), scooter.getModel(), scooter.getRegistration_number()),
                        new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())));
            }
        }

        return new ResponseEntity<>(rentalResponses, rentals.getStatusCode());
    }

    public ResponseEntity<RentalResponse> getUserRental(String username, UUID rentalUid) {
        ResponseEntity<Rental> rentalResponseEntity;
        try {
            rentalResponseEntity = rentalServiceProxy.getUserRental(username, rentalUid);
        } catch (FeignException e) {
            throw new FeignRetriesException("Rental Service unavailable");
        }
        Rental rental = rentalResponseEntity.getBody();

        Scooter scooter;
        try {
            scooter = scooterServiceProxy.getScooter(rental.getScooter_uid()).getBody();
        } catch (FeignException e) {
            scooter = null;
        }

        Payment payment;
        try {
            payment = paymentServiceProxy.getPayment(rental.getPayment_uid()).getBody();
        } catch (FeignException e) {
            payment = null;
        }

        if (payment == null && scooter == null) {
            return new ResponseEntity<>(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                    rental.getStatus(), null, null), rentalResponseEntity.getStatusCode());
        } else if (payment == null) {
            return new ResponseEntity<>(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                    rental.getStatus(), new ScooterInfo(rental.getScooter_uid(), scooter.getBrand(), scooter.getModel(), scooter.getRegistration_number()),
                    null), rentalResponseEntity.getStatusCode());
        } else if (scooter == null) {
            return new ResponseEntity<>(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                    rental.getStatus(), null,
                    new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())), rentalResponseEntity.getStatusCode());
        } else {
            return new ResponseEntity<>(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                    rental.getStatus(), new ScooterInfo(rental.getScooter_uid(), scooter.getBrand(), scooter.getModel(), scooter.getRegistration_number()),
                    new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())), rentalResponseEntity.getStatusCode());
        }
    }

    public ResponseEntity<HttpStatus> cancelUserRentalAndPaymentAndUnReserveScooter(String username, UUID rentalUid) {
        ResponseEntity<PairOfScooterUidAndPaymentUid> responseEntity;
        try {
            responseEntity = rentalServiceProxy.cancelUserRental(username, rentalUid);
        } catch (FeignException e) {
            QueueRequest newQueueRequest = new QueueRequest();
            newQueueRequest.setCancelUserRental(username, rentalUid);

            queueService.putRequestInQueue(newQueueRequest);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
            try {
                scooterServiceProxy.updateScooterReserve(responseEntity.getBody().getScooterUid(), true);
            } catch (FeignException e) {
                QueueRequest newQueueRequest = new QueueRequest();
                newQueueRequest.setUpdateScooterReserve(responseEntity.getBody().getScooterUid(), true);

                queueService.putRequestInQueue(newQueueRequest);
            }

            try {
                paymentServiceProxy.cancelPayment(responseEntity.getBody().getPaymentUid());
            } catch (FeignException e) {
                QueueRequest newQueueRequest = new QueueRequest();
                newQueueRequest.setCancelPayment(responseEntity.getBody().getPaymentUid());

                queueService.putRequestInQueue(newQueueRequest);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<HttpStatus> finishUserRentalAndUnReserveScooter(String username, UUID rentalUid) {
        ResponseEntity<UUID> responseEntity;
        try {
            responseEntity = rentalServiceProxy.finishUserRental(username, rentalUid);
        } catch (FeignException e) {
            QueueRequest newQueueRequest = new QueueRequest();
            newQueueRequest.setFinishUserRental(username, rentalUid);

            queueService.putRequestInQueue(newQueueRequest);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
            try {
                scooterServiceProxy.updateScooterReserve(responseEntity.getBody(), true);
            } catch (FeignException e) {
                QueueRequest newQueueRequest = new QueueRequest();
                newQueueRequest.setUpdateScooterReserve(responseEntity.getBody(), true);

                queueService.putRequestInQueue(newQueueRequest);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<CreateRentalResponse> reserveUserScooterAndCreateRentalAndPayment(String username, CreateRentalRequest createRentalRequest) {
        if (createRentalRequest.getScooterUid() == null || createRentalRequest.getDateFrom() == null ||
                createRentalRequest.getDateTo() == null)
            throw new ValidationErrorResponse("Invalid data");

        ResponseEntity<Scooter> scooterResponse;
        try {
            scooterResponse = scooterServiceProxy.getScooter(createRentalRequest.getScooterUid());
        } catch (FeignException e) {
            throw new FeignRetriesException("Scooter Service unavailable");
        }

        Scooter scooter = scooterResponse.getBody();
        int price = ((int) TimeUnit.DAYS.convert(createRentalRequest.getDateTo().getTime() -
                createRentalRequest.getDateFrom().getTime(), TimeUnit.MILLISECONDS)) * scooter.getPrice();

        UUID paymentUid = UUID.randomUUID();
        UUID rentalUid = UUID.randomUUID();
        try {
            scooterServiceProxy.updateScooterReserve(createRentalRequest.getScooterUid(), false);
        } catch (FeignException e) {
            throw new FeignRetriesException("Scooter Service unavailable");
        }

        try {
            rentalServiceProxy.createRental(new Rental(rentalUid, username, paymentUid, createRentalRequest.getScooterUid(),
                    createRentalRequest.getDateFrom(), createRentalRequest.getDateTo(), RentalStatus.IN_PROGRESS));
        } catch (FeignException e) {
            scooterServiceProxy.updateScooterReserve(createRentalRequest.getScooterUid(), true);
            throw new FeignRetriesException("Rental Service unavailable");
        }

        try {
            paymentServiceProxy.createPayment(new Payment(paymentUid, PaymentStatus.PAID, price));
        } catch (FeignException e) {
            rentalServiceProxy.cancelUserRental(username, rentalUid);
            scooterServiceProxy.updateScooterReserve(createRentalRequest.getScooterUid(), true);
            throw new FeignRetriesException("Payment Service unavailable");
        }

        CreateRentalResponse createRentalResponse = new CreateRentalResponse(rentalUid, createRentalRequest.getScooterUid(),
                createRentalRequest.getDateFromString(), createRentalRequest.getDateToString(),
                new PaymentInfo(paymentUid, PaymentStatus.PAID, price), RentalStatus.IN_PROGRESS);

        return new ResponseEntity<>(createRentalResponse, HttpStatus.OK);
    }

    public ResponseEntity<String> requestAuthorizationCode() {
        SessionResponse sessionResponse = authenticationProxy.
                verifyUserIdentity(new SessionRequest("alexander.drugakov@gmail.com", "Test0Okta",
                        new SessionRequest.Options(true, true))).getBody();
        authenticationProxy.requestAuthorizationCode(clientId, "code", "openid profile email",
                callbackUri, "state-vse-norm", sessionResponse.getSessionToken());
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    public ResponseEntity<TokenResponse> authorizationCodeCallback(String code, String state) {
        return authenticationProxy.exchangeCodeForTokens(clientId, clientSecret, "authorization_code",
                callbackUri, code);
    }
}