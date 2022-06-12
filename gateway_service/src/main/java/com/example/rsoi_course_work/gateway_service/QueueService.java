package com.example.rsoi_course_work.gateway_service;

import com.example.rsoi_course_work.gateway_service.model.located_scooter.LocatedScooter;
import com.example.rsoi_course_work.gateway_service.model.rental.CanceledRentalResponse;
import com.example.rsoi_course_work.gateway_service.model.rental.FinishedRentalResponse;
import com.example.rsoi_course_work.gateway_service.model.rental.Rental;
import com.example.rsoi_course_work.gateway_service.model.scooter.Scooter;
import com.example.rsoi_course_work.gateway_service.proxy.PaymentServiceProxy;
import com.example.rsoi_course_work.gateway_service.proxy.RentalServiceProxy;
import com.example.rsoi_course_work.gateway_service.proxy.ScooterServiceProxy;
import com.example.rsoi_course_work.gateway_service.proxy.StationServiceProxy;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class QueueService {
    private static BlockingQueue<QueueRequest> eventQueue = null;

    private final ScooterServiceProxy scooterServiceProxy;
    private final StationServiceProxy stationServiceProxy;
    private final RentalServiceProxy rentalServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;

    public QueueService(ScooterServiceProxy scouterServiceProxy, StationServiceProxy stationServiceProxy,
                        RentalServiceProxy rentalServiceProxy, PaymentServiceProxy paymentServiceProxy) {
        this.scooterServiceProxy = scouterServiceProxy;
        this.stationServiceProxy = stationServiceProxy;
        this.rentalServiceProxy = rentalServiceProxy;
        this.paymentServiceProxy = paymentServiceProxy;
    }

    private void initialize() {
        if (eventQueue == null) {
            eventQueue = new LinkedBlockingQueue<>();
            RequestProcessor requestProcessor = new RequestProcessor();
            requestProcessor.start();
        }
    }

    public void putRequestInQueue(QueueRequest queueRequest) {
        try {
            initialize();
            eventQueue.put(queueRequest);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    class RequestProcessor extends Thread {
        @Override
        public void run() {
            while (true) {
                QueueRequest queueRequest = null;
                try {
                    Thread.sleep(10000);
                    queueRequest = eventQueue.take();
                    switch (queueRequest.getQueueRequestType()) {
                        case CANCEL_USER_RENTAL:
                            ResponseEntity<CanceledRentalResponse> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                            try {
                                responseEntity = rentalServiceProxy.
                                        cancelUserRental(queueRequest.getUserUid(), queueRequest.getRentalUid());
                            } catch (FeignException e) {
                                QueueService.this.putRequestInQueue(queueRequest);
                            }

                            if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
                                try {
                                    stationServiceProxy.updateLocatedScooterReserve(responseEntity.getBody().getLocatedScooterUid(), true);
                                } catch (FeignException e) {
                                    QueueRequest newQueueRequest = new QueueRequest();
                                    newQueueRequest.setUpdateLocatedScooterReserve(responseEntity.getBody().getLocatedScooterUid(), true);

                                    QueueService.this.putRequestInQueue(newQueueRequest);
                                }

                                try {
                                    paymentServiceProxy.cancelPayment(responseEntity.getBody().getPaymentUid());
                                } catch (FeignException e) {
                                    QueueRequest newQueueRequest = new QueueRequest();
                                    newQueueRequest.setCancelPayment(responseEntity.getBody().getPaymentUid());

                                    QueueService.this.putRequestInQueue(newQueueRequest);
                                }
                            }
                            break;
                        case FINISH_USER_RENTAL:
                            ResponseEntity<FinishedRentalResponse> responseEntity2 = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                            try {
                                responseEntity2 = rentalServiceProxy.
                                        finishUserRental(queueRequest.getUserUid(), queueRequest.getRentalUid());
                            } catch (FeignException e) {
                                QueueService.this.putRequestInQueue(queueRequest);
                            }

                            if (responseEntity2.getStatusCode() != HttpStatus.NOT_FOUND) {
                                try {
                                    stationServiceProxy.updateLocatedScooterReserve(responseEntity2.getBody().getLocatedScooterUid(),
                                            true);
                                } catch (FeignException e) {
                                    QueueRequest newQueueRequest = new QueueRequest();
                                    newQueueRequest.setUpdateLocatedScooterReserve(responseEntity2.getBody().getLocatedScooterUid(),
                                            true);

                                    QueueService.this.putRequestInQueue(newQueueRequest);
                                }

                                try {
                                    stationServiceProxy.updateLocatedScooterRentalStation(responseEntity2.getBody().getLocatedScooterUid(),
                                            responseEntity2.getBody().getReturnToRentalStationUid());
                                } catch (FeignException e) {
                                    QueueRequest newQueueRequest = new QueueRequest();
                                    newQueueRequest.setUpdateLocatedScooterRentalStation(responseEntity2.getBody().getLocatedScooterUid(),
                                            responseEntity2.getBody().getReturnToRentalStationUid());

                                    QueueService.this.putRequestInQueue(newQueueRequest);
                                }

                                try {
                                    List<Rental> locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(responseEntity2.getBody().getLocatedScooterUid()).getBody();

                                    Rental currentRental = null;
                                    for (Rental rental : locatedScooterRentals) {
                                        if (rental.getRental_uid() == queueRequest.getRentalUid()) {
                                            currentRental = rental;
                                        }
                                    }

                                    Rental lastRental = null;
                                    for (Rental rental : locatedScooterRentals) {
                                        if (rental.getRental_uid() != queueRequest.getRentalUid()) {
                                            if (rental.getDate_to().before(currentRental.getDate_from()) &&
                                                    (lastRental == null || lastRental.getDate_to().before(rental.getDate_to()))) {
                                                lastRental = rental;
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

                                    stationServiceProxy.updateLocatedScooterCurrentCharge(responseEntity2.getBody().getLocatedScooterUid(),
                                            currentCharge);
                                } catch (FeignException e) {
                                    QueueRequest newQueueRequest = new QueueRequest();
                                    newQueueRequest.setUpdateLocatedScooterCurrentCharge(responseEntity2.getBody().getLocatedScooterUid(),
                                            queueRequest.getRentalUid());

                                    QueueService.this.putRequestInQueue(newQueueRequest);
                                }
                            }
                            break;
                        case UPDATE_SCOOTER_RESERVE:
                            try {
                                stationServiceProxy.updateLocatedScooterReserve(queueRequest.getLocatedScooterUid(),
                                        queueRequest.isAvailability());
                            } catch (FeignException e) {
                                QueueService.this.putRequestInQueue(queueRequest);
                            }
                            break;
                        case UPDATE_SCOOTER_RENTAL_STATION:
                            try {
                                stationServiceProxy.updateLocatedScooterRentalStation(queueRequest.getLocatedScooterUid(),
                                        queueRequest.getRentalStationUid());
                            } catch (FeignException e) {
                                QueueService.this.putRequestInQueue(queueRequest);
                            }
                            break;
                        case UPDATE_SCOOTER_CURRENT_CHARGE:
                            try {
                                List<Rental> locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(queueRequest.getLocatedScooterUid()).getBody();

                                Rental currentRental = null;
                                for (Rental rental : locatedScooterRentals) {
                                    if (rental.getRental_uid() == queueRequest.getRentalUid()) {
                                        currentRental = rental;
                                    }
                                }

                                Rental lastRental = null;
                                for (Rental rental : locatedScooterRentals) {
                                    if (rental.getRental_uid() != queueRequest.getRentalUid()) {
                                        if (rental.getDate_to().before(currentRental.getDate_from()) &&
                                                (lastRental == null || lastRental.getDate_to().before(rental.getDate_to()))) {
                                            lastRental = rental;
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

                                stationServiceProxy.updateLocatedScooterCurrentCharge(queueRequest.getLocatedScooterUid(),
                                        currentCharge);
                            } catch (FeignException e) {
                                QueueService.this.putRequestInQueue(queueRequest);
                            }
                            break;
                        case CANCEL_PAYMENT:
                            try {
                                paymentServiceProxy.cancelPayment(queueRequest.getPaymentUid());
                            } catch (FeignException e) {
                                QueueService.this.putRequestInQueue(queueRequest);
                            }
                            break;
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
