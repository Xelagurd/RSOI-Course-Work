package com.example.rsoi_course_work.front;

import com.example.rsoi_course_work.front.exception.FeignRetriesException;
import com.example.rsoi_course_work.front.model.located_scooter.CreateLocatedScooterRequest;
import com.example.rsoi_course_work.front.model.located_scooter.LocatedScooterInfo;
import com.example.rsoi_course_work.front.model.located_scooter.PaginationResponse;
import com.example.rsoi_course_work.front.model.rental.CreateRentalRequest;
import com.example.rsoi_course_work.front.model.rental.RentalInfo;
import com.example.rsoi_course_work.front.model.rental_station.CreateRentalStationRequest;
import com.example.rsoi_course_work.front.model.scooter.CreateScooterRequest;
import com.example.rsoi_course_work.front.model.statistic_operation.ServiceType;
import com.example.rsoi_course_work.front.model.statistic_operation.StatisticOperation;
import com.example.rsoi_course_work.front.model.statistic_operation.StatisticOperationInfo;
import com.example.rsoi_course_work.front.model.statistic_operation.StatisticOperationType;
import com.example.rsoi_course_work.front.model.user.UserInfo;
import com.example.rsoi_course_work.front.model.user.security.RegistrationRequest;
import com.example.rsoi_course_work.front.model.user.security.SessionJwtResponse;
import com.example.rsoi_course_work.front.proxy.GatewayServiceProxy;
import com.example.rsoi_course_work.front.proxy.SessionServiceProxy;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FrontService {
    private final GatewayServiceProxy gatewayServiceProxy;
    private final SessionServiceProxy sessionServiceProxy;

    public FrontService(GatewayServiceProxy scooterServiceProxy, SessionServiceProxy sessionServiceProxy) {
        this.gatewayServiceProxy = scooterServiceProxy;
        this.sessionServiceProxy = sessionServiceProxy;
    }

    public String getJWT() {
        StringBuilder jwt = new StringBuilder("Bearer ");
        Cookie[] cookies = VaadinRequest.getCurrent().getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                jwt.append(cookie.getValue());
            }
        }

        return jwt.toString();
    }

    public ResponseEntity<SessionJwtResponse> userAuthorization(String login, String password) {
        try {
            ResponseEntity<SessionJwtResponse> responseEntity = sessionServiceProxy.userAuthorization(login, password);

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                SessionJwtResponse sessionJwtResponse = responseEntity.getBody();
                Cookie myCookie = new Cookie("Authorization", sessionJwtResponse.getJwt());
                myCookie.setMaxAge(60 * 60);
                myCookie.setPath("/");
                VaadinResponse.getCurrent().addCookie(myCookie);

                return responseEntity;
            }

            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (FeignException e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<SessionJwtResponse> userRegistration(RegistrationRequest registrationRequest) {
        try {
            ResponseEntity<SessionJwtResponse> responseEntity = sessionServiceProxy.userRegistration(registrationRequest);

            if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                SessionJwtResponse sessionJwtResponse = responseEntity.getBody();
                Cookie myCookie = new Cookie("Authorization", sessionJwtResponse.getJwt());
                myCookie.setMaxAge(60 * 60);
                myCookie.setPath("/");
                VaadinResponse.getCurrent().addCookie(myCookie);

                try {
                    gatewayServiceProxy.createStatisticOperation(getJWT(), new StatisticOperation(
                            UUID.randomUUID(), ServiceType.SESSION, StatisticOperationType.CREATE,
                            new Date(), sessionJwtResponse.getUser_uid(), null, null,
                            null, null, null));

                    return responseEntity;
                } catch (FeignException e) {
                    return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
                }
            }

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FeignException e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<UserInfo> getCurrentUser() {
        try {
            return gatewayServiceProxy.getCurrentUser(getJWT());
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<Boolean> verifyJwtToken(String jwt) {
        try {
            return gatewayServiceProxy.verifyJwtToken(jwt);
        } catch (FeignException e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<StatisticOperationInfo>> getStatisticOperations() {
        try {
            return gatewayServiceProxy.getStatisticOperations(getJWT());
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<HttpStatus> createScooter(CreateScooterRequest createScooterRequest) {
        try {
            return gatewayServiceProxy.createScooter(getJWT(), createScooterRequest);
        } catch (
                FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<HttpStatus> removeScooter(UUID scooterUid) {
        try {
            return gatewayServiceProxy.removeScooter(getJWT(), scooterUid);
        } catch (
                FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<HttpStatus> createRentalStation(CreateRentalStationRequest createRentalStationRequest) {
        try {
            return gatewayServiceProxy.createRentalStation(getJWT(), createRentalStationRequest);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<HttpStatus> removeRentalStation(UUID rentalStationUid) {
        try {
            return gatewayServiceProxy.removeRentalStation(getJWT(), rentalStationUid);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<HttpStatus> createLocatedScooter(CreateLocatedScooterRequest createLocatedScooterRequest) {
        try {
            return gatewayServiceProxy.createLocatedScooter(getJWT(), createLocatedScooterRequest);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<HttpStatus> removeLocatedScooter(UUID locatedScooterUid) {
        try {
            return gatewayServiceProxy.removeLocatedScooter(getJWT(), locatedScooterUid);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<LocatedScooterInfo> getLocatedScooter(UUID locatedScooterUid) {
        try {
            return gatewayServiceProxy.getLocatedScooter(getJWT(), locatedScooterUid);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<PaginationResponse> getLocatedScooters(Integer page,
                                                                 Integer size,
                                                                 Boolean showAll) {
        try {
            return gatewayServiceProxy.getLocatedScooters(getJWT(), page, size, showAll);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<List<RentalInfo>> getUserRentals() {
        try {
            return gatewayServiceProxy.getUserRentals(getJWT());
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<RentalInfo> getUserRental(UUID rentalUid) {
        try {
            return gatewayServiceProxy.getUserRental(getJWT(), rentalUid);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<HttpStatus> cancelUserRentalAndPaymentAndUnReserveScooter(UUID rentalUid) {
        try {
            return gatewayServiceProxy.cancelUserRentalAndPaymentAndUnReserveScooter(getJWT(), rentalUid);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<HttpStatus> finishUserRentalAndUnReserveScooter(UUID rentalUid) {
        try {
            return gatewayServiceProxy.finishUserRentalAndUnReserveScooter(getJWT(), rentalUid);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }

    public ResponseEntity<RentalInfo> reserveUserScooterAndCreateRentalAndPayment(CreateRentalRequest createRentalRequest) {
        try {
            return gatewayServiceProxy.reserveUserScooterAndCreateRentalAndPayment(getJWT(), createRentalRequest);
        } catch (FeignException e) {
            throw new FeignRetriesException("Gateway Service unavailable");
        }
    }
}
