package com.example.rsoi_course_work.gateway_service;

import com.example.rsoi_course_work.gateway_service.exception.FeignRetriesException;
import com.example.rsoi_course_work.gateway_service.exception.ValidationException;
import com.example.rsoi_course_work.gateway_service.model.*;
import com.example.rsoi_course_work.gateway_service.proxy.*;
import feign.FeignException;
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


    public ResponseEntity<PaginationResponse> getLocatedScooters(String jwt, Integer page, Integer size, Boolean showAll) {
        ResponseEntity<UserInfo> userResponseEntity = getCurrentUser(jwt);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            ResponseEntity<LocatedScooterPartialList> answer;
            try {
                answer = stationServiceProxy.getLocatedScooters(page, size, showAll);
            } catch (FeignException e) {
                throw new FeignRetriesException("LocatedScooter Service unavailable");
            }

            List<LocatedScooterInfo> locatedScooterRespons = new ArrayList<>();
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

                if (scooter != null)
                    locatedScooterInfo.setScooter(scooter.getInfo());

                if (rental_station != null)
                    locatedScooterInfo.setRental_station(rental_station.getInfo());

                locatedScooterRespons.add(locatedScooterInfo);
            }

            return new ResponseEntity<>(new PaginationResponse(page, size, answer.getBody().getTotalElements(), locatedScooterRespons), answer.getStatusCode());
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

                    if (scooter != null)
                        locatedScooterInfo.setScooter(scooter.getInfo());

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

                if (scooter != null)
                    locatedScooterInfo.setScooter(scooter.getInfo());

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
            ResponseEntity<PairOfLocatedScooterUidAndPaymentUid> responseEntity;
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
                    //TODO: обновлять топливо и место
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
            ResponseEntity<UUID> responseEntity;
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
                    //TODO: обновлять топливо и место
                    stationServiceProxy.updateLocatedScooterReserve(responseEntity.getBody(), true);
                } catch (FeignException e) {
                    QueueRequest newQueueRequest = new QueueRequest();
                    newQueueRequest.setUpdateLocatedScooterReserve(responseEntity.getBody(), true);

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
                //TODO: обновлять топливо и место
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
                //TODO: обновлять топливо и место
                stationServiceProxy.updateLocatedScooterReserve(createRentalRequest.getLocated_scooter_uid(), true);
                throw new FeignRetriesException("Rental Service unavailable");
            }

            Payment payment = new Payment(paymentUid, price, PaymentStatus.PAID);
            try {
                paymentServiceProxy.createPayment(payment);
            } catch (FeignException e) {
                //TODO: обновлять топливо и место
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