package com.example.rsoi_course_work.gateway_service;

import com.example.rsoi_course_work.gateway_service.exception.FeignRetriesException;
import com.example.rsoi_course_work.gateway_service.exception.ValidationException;
import com.example.rsoi_course_work.gateway_service.model.located_scooter.*;
import com.example.rsoi_course_work.gateway_service.model.payment.Payment;
import com.example.rsoi_course_work.gateway_service.model.payment.PaymentStatus;
import com.example.rsoi_course_work.gateway_service.model.rental.*;
import com.example.rsoi_course_work.gateway_service.model.rental_station.CreateRentalStationRequest;
import com.example.rsoi_course_work.gateway_service.model.rental_station.RentalStation;
import com.example.rsoi_course_work.gateway_service.model.scooter.CreateScooterRequest;
import com.example.rsoi_course_work.gateway_service.model.scooter.Scooter;
import com.example.rsoi_course_work.gateway_service.model.user.User;
import com.example.rsoi_course_work.gateway_service.model.user.UserInfo;
import com.example.rsoi_course_work.gateway_service.model.user.UserRole;
import com.example.rsoi_course_work.gateway_service.proxy.*;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class GatewayService {
    private final ScooterServiceProxy scooterServiceProxy;
    private final StationServiceProxy stationServiceProxy;
    private final RentalServiceProxy rentalServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;
    private final SessionServiceProxy sessionServiceProxy;
    private final QueueService queueService;
    //TODO: очередь статистики

    public GatewayService(ScooterServiceProxy scooterServiceProxy, StationServiceProxy stationServiceProxy,
                          RentalServiceProxy rentalServiceProxy, PaymentServiceProxy paymentServiceProxy,
                          SessionServiceProxy sessionServiceProxy, QueueService queueService) {
        this.scooterServiceProxy = scooterServiceProxy;
        this.stationServiceProxy = stationServiceProxy;
        this.rentalServiceProxy = rentalServiceProxy;
        this.paymentServiceProxy = paymentServiceProxy;
        this.sessionServiceProxy = sessionServiceProxy;
        this.queueService = queueService;
    }

    public ResponseEntity<UserInfo> getCurrentUser(String jwt) {
        ResponseEntity<Boolean> answer;
        try {
            answer = sessionServiceProxy.verifyJwtToken(jwt);
        } catch (FeignException e) {
            throw new FeignRetriesException("Session Service unavailable");
        }

        if (Boolean.TRUE.equals(answer.getBody())) {
            try {
                User user = sessionServiceProxy.getCurrentUser(jwt).getBody();
                return new ResponseEntity<>(user.getInfo(), HttpStatus.OK);
            } catch (FeignException e) {
                throw new FeignRetriesException("Session Service unavailable");
            }
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<HttpStatus> createScooter(String jwt, CreateScooterRequest createScooterRequest) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (userResponseEntity.getBody().getRole().equals(UserRole.ADMIN)) {
                UUID scooterUid = UUID.randomUUID();
                Scooter scooter = new Scooter(scooterUid, createScooterRequest.getProvider(),
                        createScooterRequest.getMax_speed(), createScooterRequest.getPrice(),
                        createScooterRequest.getCharge_recovery(), createScooterRequest.getCharge_consumption());
                try {
                    return scooterServiceProxy.createScooter(scooter);
                } catch (FeignException e) {
                    throw new FeignRetriesException("Scooter Service unavailable");
                }
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<HttpStatus> removeScooter(String jwt, UUID scooterUid) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (userResponseEntity.getBody().getRole().equals(UserRole.ADMIN)) {
                try {
                    return scooterServiceProxy.removeScooter(scooterUid);
                } catch (FeignException e) {
                    throw new FeignRetriesException("Scooter Service unavailable");
                }
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<HttpStatus> createRentalStation(String jwt, CreateRentalStationRequest createRentalStationRequest) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (userResponseEntity.getBody().getRole().equals(UserRole.ADMIN)) {
                UUID rentalStationUid = UUID.randomUUID();
                RentalStation rentalStation = new RentalStation(rentalStationUid,
                        createRentalStationRequest.getLocation());
                try {
                    return stationServiceProxy.createRentalStation(rentalStation);
                } catch (FeignException e) {
                    throw new FeignRetriesException("Station Service unavailable");
                }
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<HttpStatus> removeRentalStation(String jwt, UUID rentalStationUid) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (userResponseEntity.getBody().getRole().equals(UserRole.ADMIN)) {
                try {
                    return stationServiceProxy.removeRentalStation(rentalStationUid);
                } catch (FeignException e) {
                    throw new FeignRetriesException("Station Service unavailable");
                }
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<HttpStatus> createLocatedScooter(String jwt, CreateLocatedScooterRequest createLocatedScooterRequest) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (userResponseEntity.getBody().getRole().equals(UserRole.ADMIN)) {
                UUID locatedScooterUid = UUID.randomUUID();
                LocatedScooter locatedScooter = new LocatedScooter(locatedScooterUid,
                        createLocatedScooterRequest.getScooter_uid(), createLocatedScooterRequest.getRental_station_uid(),
                        createLocatedScooterRequest.getRegistration_number(), 100, true);
                try {
                    return stationServiceProxy.createLocatedScooter(locatedScooter);
                } catch (FeignException e) {
                    throw new FeignRetriesException("Station Service unavailable");
                }
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<HttpStatus> removeLocatedScooter(String jwt, UUID locatedScooterUid) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (userResponseEntity.getBody().getRole().equals(UserRole.ADMIN)) {
                try {
                    return stationServiceProxy.removeLocatedScooter(locatedScooterUid);
                } catch (FeignException e) {
                    throw new FeignRetriesException("Station Service unavailable");
                }
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<PaginationResponse> getLocatedScooters(String jwt, Integer page, Integer size, Boolean showAll) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            ResponseEntity<LocatedScooterPartialList> answer;
            try {
                answer = stationServiceProxy.getLocatedScooters(page, size, showAll);
            } catch (FeignException e) {
                throw new FeignRetriesException("LocatedScooter Service unavailable");
            }

            List<LocatedScooterInfo> locatedScooterResponse = new ArrayList<>();
            for (LocatedScooter locatedScooter : answer.getBody().getLocatedScooters()) {
                Scooter scooter;
                try {
                    scooter = scooterServiceProxy.getScooter(locatedScooter.getScooter_uid()).getBody();
                } catch (FeignException e) {
                    scooter = null;
                }

                RentalStation rental_station;
                try {
                    rental_station = stationServiceProxy.getRentalStation(locatedScooter.getRental_station_uid()).getBody();
                } catch (FeignException e) {
                    rental_station = null;
                }

                LocatedScooterInfo locatedScooterInfo = locatedScooter.getInfo();

                if (scooter != null) {
                    locatedScooterInfo.setScooter(scooter.getInfo());

                    List<Rental> locatedScooterRentals;
                    try {
                        locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(locatedScooter.getLocated_scooter_uid()).getBody();
                    } catch (FeignException e) {
                        locatedScooterRentals = null;
                    }

                    if (locatedScooterRentals != null) {
                        Date currentDate = new Date();
                        Rental lastRental = null;
                        for (Rental rentalTemp : locatedScooterRentals) {
                            if (rentalTemp.getDate_to().before(currentDate) &&
                                    (lastRental == null || lastRental.getDate_to().before(rentalTemp.getDate_to()))) {
                                lastRental = rentalTemp;
                            }
                        }

                        Integer currentCharge;
                        if (lastRental == null) {
                            currentCharge = 100;
                        } else {
                            currentCharge = locatedScooter.getCurrent_charge() + (int) (((int) TimeUnit.MINUTES.convert(currentDate.getTime() -
                                    lastRental.getDate_to().getTime(), TimeUnit.MILLISECONDS)) * 0.1 * scooter.getCharge_recovery());
                        }

                        currentCharge = currentCharge > 100 ? 100 : currentCharge;

                        locatedScooterInfo.setCurrent_charge(currentCharge);
                    }
                }

                if (rental_station != null)
                    locatedScooterInfo.setRental_station(rental_station.getInfo());

                if (locatedScooterInfo.getCurrent_charge() > 0)
                    locatedScooterResponse.add(locatedScooterInfo);
            }

            return new ResponseEntity<>(new PaginationResponse(page, size, answer.getBody().getTotalElements(), locatedScooterResponse), answer.getStatusCode());
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<List<RentalInfo>> getUserRentals(String jwt) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            ResponseEntity<List<Rental>> rentals;
            try {
                rentals = rentalServiceProxy.getUserRentals(userResponseEntity.getBody().getUser_uid());
            } catch (FeignException e) {
                throw new FeignRetriesException("Rental Service unavailable");
            }

            List<RentalInfo> rentalRespons = new ArrayList<>();
            for (Rental rental : rentals.getBody()) {
                LocatedScooter locatedScooter;
                try {
                    locatedScooter = stationServiceProxy.getLocatedScooter(rental.getLocated_scooter_uid()).getBody();
                } catch (FeignException e) {
                    locatedScooter = null;
                }

                Payment payment;
                try {
                    payment = paymentServiceProxy.getPayment(rental.getPayment_uid()).getBody();
                } catch (FeignException e) {
                    payment = null;
                }

                RentalStation taken_from_rental_station;
                try {
                    taken_from_rental_station = stationServiceProxy.getRentalStation(rental.getTaken_from()).getBody();
                } catch (FeignException e) {
                    taken_from_rental_station = null;
                }

                RentalStation return_to_rental_station;
                try {
                    return_to_rental_station = stationServiceProxy.getRentalStation(rental.getReturn_to()).getBody();
                } catch (FeignException e) {
                    return_to_rental_station = null;
                }

                RentalInfo rentalInfo = rental.getInfo();

                rentalInfo.setUser(userResponseEntity.getBody());

                if (locatedScooter != null) {
                    Scooter scooter;
                    try {
                        scooter = scooterServiceProxy.getScooter(locatedScooter.getScooter_uid()).getBody();
                    } catch (FeignException e) {
                        scooter = null;
                    }

                    RentalStation rental_station;
                    try {
                        rental_station = stationServiceProxy.getRentalStation(locatedScooter.getRental_station_uid()).getBody();
                    } catch (FeignException e) {
                        rental_station = null;
                    }

                    LocatedScooterInfo locatedScooterInfo = locatedScooter.getInfo();

                    if (scooter != null) {
                        locatedScooterInfo.setScooter(scooter.getInfo());

                        List<Rental> locatedScooterRentals;
                        try {
                            locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(locatedScooter.getLocated_scooter_uid()).getBody();
                        } catch (FeignException e) {
                            locatedScooterRentals = null;
                        }

                        if (locatedScooterRentals != null) {
                            Date currentDate = new Date();
                            Rental lastRental = null;
                            for (Rental rentalTemp : locatedScooterRentals) {
                                if (rentalTemp.getDate_to().before(currentDate) &&
                                        (lastRental == null || lastRental.getDate_to().before(rentalTemp.getDate_to()))) {
                                    lastRental = rentalTemp;
                                }
                            }

                            Integer currentCharge;
                            if (lastRental == null) {
                                currentCharge = 100;
                            } else {
                                currentCharge = locatedScooter.getCurrent_charge() + (int) (((int) TimeUnit.MINUTES.convert(currentDate.getTime() -
                                        lastRental.getDate_to().getTime(), TimeUnit.MILLISECONDS)) * 0.1 * scooter.getCharge_recovery());
                            }

                            currentCharge = currentCharge > 100 ? 100 : currentCharge;

                            locatedScooterInfo.setCurrent_charge(currentCharge);
                        }
                    }

                    if (rental_station != null)
                        locatedScooterInfo.setRental_station(rental_station.getInfo());

                    rentalInfo.setLocated_scooter(locatedScooterInfo);
                }

                if (payment != null)
                    rentalInfo.setPayment(payment.getInfo());

                if (taken_from_rental_station != null)
                    rentalInfo.setTakenFromRentalStation(taken_from_rental_station.getInfo());

                if (return_to_rental_station != null)
                    rentalInfo.setReturnToRentalStation(return_to_rental_station.getInfo());

                rentalRespons.add(rentalInfo);
            }

            return new ResponseEntity<>(rentalRespons, rentals.getStatusCode());
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<RentalInfo> getUserRental(String jwt, UUID rentalUid) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            ResponseEntity<Rental> rentalResponseEntity;
            try {
                rentalResponseEntity = rentalServiceProxy.getUserRental(userResponseEntity.getBody().getUser_uid(), rentalUid);
            } catch (FeignException e) {
                throw new FeignRetriesException("Rental Service unavailable");
            }
            Rental rental = rentalResponseEntity.getBody();

            LocatedScooter locatedScooter;
            try {
                locatedScooter = stationServiceProxy.getLocatedScooter(rental.getLocated_scooter_uid()).getBody();
            } catch (FeignException e) {
                locatedScooter = null;
            }

            Payment payment;
            try {
                payment = paymentServiceProxy.getPayment(rental.getPayment_uid()).getBody();
            } catch (FeignException e) {
                payment = null;
            }

            RentalStation taken_from_rental_station;
            try {
                taken_from_rental_station = stationServiceProxy.getRentalStation(rental.getTaken_from()).getBody();
            } catch (FeignException e) {
                taken_from_rental_station = null;
            }

            RentalStation return_to_rental_station;
            try {
                return_to_rental_station = stationServiceProxy.getRentalStation(rental.getReturn_to()).getBody();
            } catch (FeignException e) {
                return_to_rental_station = null;
            }

            RentalInfo rentalInfo = rental.getInfo();

            rentalInfo.setUser(userResponseEntity.getBody());

            if (locatedScooter != null) {
                Scooter scooter;
                try {
                    scooter = scooterServiceProxy.getScooter(locatedScooter.getScooter_uid()).getBody();
                } catch (FeignException e) {
                    scooter = null;
                }

                RentalStation rental_station;
                try {
                    rental_station = stationServiceProxy.getRentalStation(locatedScooter.getRental_station_uid()).getBody();
                } catch (FeignException e) {
                    rental_station = null;
                }

                LocatedScooterInfo locatedScooterInfo = locatedScooter.getInfo();

                if (scooter != null) {
                    locatedScooterInfo.setScooter(scooter.getInfo());

                    List<Rental> locatedScooterRentals;
                    try {
                        locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(locatedScooter.getLocated_scooter_uid()).getBody();
                    } catch (FeignException e) {
                        locatedScooterRentals = null;
                    }

                    if (locatedScooterRentals != null) {
                        Date currentDate = new Date();
                        Rental lastRental = null;
                        for (Rental rentalTemp : locatedScooterRentals) {
                            if (rentalTemp.getDate_to().before(currentDate) &&
                                    (lastRental == null || lastRental.getDate_to().before(rentalTemp.getDate_to()))) {
                                lastRental = rentalTemp;
                            }
                        }

                        Integer currentCharge;
                        if (lastRental == null) {
                            currentCharge = 100;
                        } else {
                            currentCharge = locatedScooter.getCurrent_charge() + (int) (((int) TimeUnit.MINUTES.convert(currentDate.getTime() -
                                    lastRental.getDate_to().getTime(), TimeUnit.MILLISECONDS)) * 0.1 * scooter.getCharge_recovery());
                        }

                        currentCharge = currentCharge > 100 ? 100 : currentCharge;

                        locatedScooterInfo.setCurrent_charge(currentCharge);
                    }
                }
                if (rental_station != null)
                    locatedScooterInfo.setRental_station(rental_station.getInfo());

                rentalInfo.setLocated_scooter(locatedScooterInfo);
            }

            if (payment != null)
                rentalInfo.setPayment(payment.getInfo());

            if (taken_from_rental_station != null)
                rentalInfo.setTakenFromRentalStation(taken_from_rental_station.getInfo());

            if (return_to_rental_station != null)
                rentalInfo.setReturnToRentalStation(return_to_rental_station.getInfo());

            return new ResponseEntity<>(rentalInfo, rentalResponseEntity.getStatusCode());
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<HttpStatus> cancelUserRentalAndPaymentAndUnReserveScooter(String jwt, UUID rentalUid) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            ResponseEntity<CanceledRentalResponse> responseEntity;
            try {
                responseEntity = rentalServiceProxy.cancelUserRental(userResponseEntity.getBody().getUser_uid(), rentalUid);
            } catch (FeignException e) {
                QueueRequest newQueueRequest = new QueueRequest();
                newQueueRequest.setCancelUserRental(userResponseEntity.getBody().getUser_uid(), rentalUid);

                queueService.putRequestInQueue(newQueueRequest);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
                try {
                    stationServiceProxy.updateLocatedScooterReserve(responseEntity.getBody().getLocatedScooterUid(), true);
                } catch (FeignException e) {
                    QueueRequest newQueueRequest = new QueueRequest();
                    newQueueRequest.setUpdateLocatedScooterReserve(responseEntity.getBody().getLocatedScooterUid(), true);

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

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<HttpStatus> finishUserRentalAndUnReserveScooter(String jwt, UUID rentalUid) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            ResponseEntity<FinishedRentalResponse> responseEntity;
            try {
                responseEntity = rentalServiceProxy.finishUserRental(userResponseEntity.getBody().getUser_uid(), rentalUid);
            } catch (FeignException e) {
                QueueRequest newQueueRequest = new QueueRequest();
                newQueueRequest.setFinishUserRental(userResponseEntity.getBody().getUser_uid(), rentalUid);

                queueService.putRequestInQueue(newQueueRequest);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
                try {
                    stationServiceProxy.updateLocatedScooterReserve(responseEntity.getBody().getLocatedScooterUid(),
                            true);
                } catch (FeignException e) {
                    QueueRequest newQueueRequest = new QueueRequest();
                    newQueueRequest.setUpdateLocatedScooterReserve(responseEntity.getBody().getLocatedScooterUid(),
                            true);

                    queueService.putRequestInQueue(newQueueRequest);
                }

                try {
                    stationServiceProxy.updateLocatedScooterRentalStation(responseEntity.getBody().getLocatedScooterUid(),
                            responseEntity.getBody().getReturnToRentalStationUid());
                } catch (FeignException e) {
                    QueueRequest newQueueRequest = new QueueRequest();
                    newQueueRequest.setUpdateLocatedScooterRentalStation(responseEntity.getBody().getLocatedScooterUid(),
                            responseEntity.getBody().getReturnToRentalStationUid());

                    queueService.putRequestInQueue(newQueueRequest);
                }

                try {
                    List<Rental> locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(responseEntity.getBody().getLocatedScooterUid()).getBody();

                    Rental currentRental = null;
                    for (Rental rental : locatedScooterRentals) {
                        if (rental.getRental_uid() == rentalUid) {
                            currentRental = rental;
                        }
                    }

                    Rental lastRental = null;
                    for (Rental rentalTemp : locatedScooterRentals) {
                        if (rentalTemp.getRental_uid() != rentalUid) {
                            if (rentalTemp.getDate_to().before(currentRental.getDate_from()) &&
                                    (lastRental == null || lastRental.getDate_to().before(rentalTemp.getDate_to()))) {
                                lastRental = rentalTemp;
                            }
                        }
                    }

                    LocatedScooter locatedScooter = stationServiceProxy.getLocatedScooter(lastRental.getLocated_scooter_uid()).getBody();
                    Scooter scooter = scooterServiceProxy.getScooter(locatedScooter.getScooter_uid()).getBody();

                    Integer currentCharge;
                    if (lastRental == null) {
                        currentCharge = 100;
                    } else {
                        currentCharge = locatedScooter.getCurrent_charge() + (int) (((int) TimeUnit.MINUTES.convert(currentRental.getDate_from().getTime() -
                                lastRental.getDate_to().getTime(), TimeUnit.MILLISECONDS)) * 0.1 * scooter.getCharge_recovery());
                    }

                    currentCharge = currentCharge > 100 ? 100 : currentCharge;

                    currentCharge = currentCharge - (int) (((int) TimeUnit.MINUTES.convert(currentRental.getDate_to().getTime() -
                            currentRental.getDate_from().getTime(), TimeUnit.MILLISECONDS)) * 0.1 * scooter.getCharge_consumption());

                    currentCharge = currentCharge < 0 ? 0 : currentCharge;

                    stationServiceProxy.updateLocatedScooterCurrentCharge(responseEntity.getBody().getLocatedScooterUid(),
                            currentCharge);
                } catch (FeignException e) {
                    QueueRequest newQueueRequest = new QueueRequest();
                    newQueueRequest.setUpdateLocatedScooterCurrentCharge(responseEntity.getBody().getLocatedScooterUid(),
                            rentalUid);

                    queueService.putRequestInQueue(newQueueRequest);
                }
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<RentalInfo> reserveUserScooterAndCreateRentalAndPayment(String jwt, CreateRentalRequest createRentalRequest) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (!createRentalRequest.isValid())
                throw new ValidationException("Invalid data");

            ResponseEntity<LocatedScooter> locatedScooterResponse;
            try {
                locatedScooterResponse = stationServiceProxy.getLocatedScooter(createRentalRequest.getLocated_scooter_uid());
            } catch (FeignException e) {
                throw new FeignRetriesException("Station Service unavailable");
            }

            ResponseEntity<Scooter> scooterResponse;
            try {
                scooterResponse = scooterServiceProxy.getScooter(locatedScooterResponse.getBody().getScooter_uid());
            } catch (FeignException e) {
                throw new FeignRetriesException("Scooter Service unavailable");
            }

            LocatedScooter locatedScooter = locatedScooterResponse.getBody();
            Scooter scooter = scooterResponse.getBody();
            int price = ((int) TimeUnit.DAYS.convert(createRentalRequest.getDate_to().getTime() -
                    createRentalRequest.getDate_from().getTime(), TimeUnit.MILLISECONDS)) * scooter.getPrice();

            UUID paymentUid = UUID.randomUUID();
            UUID rentalUid = UUID.randomUUID();
            try {
                stationServiceProxy.updateLocatedScooterReserve(createRentalRequest.getLocated_scooter_uid(), false);
            } catch (FeignException e) {
                throw new FeignRetriesException("Station Service unavailable");
            }

            Rental rental = new Rental(rentalUid, userResponseEntity.getBody().getUser_uid(), createRentalRequest.getLocated_scooter_uid(),
                    paymentUid, locatedScooter.getRental_station_uid(), createRentalRequest.getReturn_to(),
                    createRentalRequest.getDate_from(), createRentalRequest.getDate_to(),
                    RentalStatus.IN_PROGRESS);
            try {
                rentalServiceProxy.createRental(rental);
            } catch (FeignException e) {
                stationServiceProxy.updateLocatedScooterReserve(createRentalRequest.getLocated_scooter_uid(), true);
                throw new FeignRetriesException("Rental Service unavailable");
            }

            Payment payment = new Payment(paymentUid, price, PaymentStatus.PAID);
            try {
                paymentServiceProxy.createPayment(payment);
            } catch (FeignException e) {
                rentalServiceProxy.cancelUserRental(userResponseEntity.getBody().getUser_uid(), rentalUid);
                stationServiceProxy.updateLocatedScooterReserve(createRentalRequest.getLocated_scooter_uid(), true);
                throw new FeignRetriesException("Payment Service unavailable");
            }

            RentalStation taken_from_rental_station;
            try {
                taken_from_rental_station = stationServiceProxy.getRentalStation(rental.getTaken_from()).getBody();
            } catch (FeignException e) {
                taken_from_rental_station = null;
            }

            RentalStation return_to_rental_station;
            try {
                return_to_rental_station = stationServiceProxy.getRentalStation(rental.getReturn_to()).getBody();
            } catch (FeignException e) {
                return_to_rental_station = null;
            }

            RentalInfo rentalInfo = rental.getInfo();

            rentalInfo.setUser(userResponseEntity.getBody());

            RentalStation rental_station;
            try {
                rental_station = stationServiceProxy.getRentalStation(locatedScooter.getRental_station_uid()).getBody();
            } catch (FeignException e) {
                rental_station = null;
            }

            LocatedScooterInfo locatedScooterInfo = locatedScooter.getInfo();
            locatedScooterInfo.setScooter(scooter.getInfo());

            List<Rental> locatedScooterRentals;
            try {
                locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(locatedScooter.getLocated_scooter_uid()).getBody();
            } catch (FeignException e) {
                locatedScooterRentals = null;
            }

            if (locatedScooterRentals != null) {
                Date currentDate = new Date();
                Rental lastRental = null;
                for (Rental rentalTemp : locatedScooterRentals) {
                    if (rentalTemp.getDate_to().before(currentDate) &&
                            (lastRental == null || lastRental.getDate_to().before(rentalTemp.getDate_to()))) {
                        lastRental = rentalTemp;
                    }
                }

                Integer currentCharge;
                if (lastRental == null) {
                    currentCharge = 100;
                } else {
                    currentCharge = locatedScooter.getCurrent_charge() + (int) (((int) TimeUnit.MINUTES.convert(currentDate.getTime() -
                            lastRental.getDate_to().getTime(), TimeUnit.MILLISECONDS)) * 0.1 * scooter.getCharge_recovery());
                }

                currentCharge = currentCharge > 100 ? 100 : currentCharge;

                locatedScooterInfo.setCurrent_charge(currentCharge);
            }

            if (rental_station != null)
                locatedScooterInfo.setRental_station(rental_station.getInfo());

            rentalInfo.setLocated_scooter(locatedScooterInfo);

            rentalInfo.setPayment(payment.getInfo());

            if (taken_from_rental_station != null)
                rentalInfo.setTakenFromRentalStation(taken_from_rental_station.getInfo());

            if (return_to_rental_station != null)
                rentalInfo.setReturnToRentalStation(return_to_rental_station.getInfo());

            return new ResponseEntity<>(rentalInfo, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}