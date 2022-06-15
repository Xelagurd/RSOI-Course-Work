package com.example.rsoi_course_work.gateway_service;

import com.example.rsoi_course_work.gateway_service.model.located_scooter.LocatedScooter;
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
public class DeferredConnectionsQueue {
    private static BlockingQueue<DeferredConnectionRequest> eventQueue = null;

    private final ScooterServiceProxy scooterServiceProxy;
    private final StationServiceProxy stationServiceProxy;
    private final RentalServiceProxy rentalServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;

    public DeferredConnectionsQueue(ScooterServiceProxy scouterServiceProxy, StationServiceProxy stationServiceProxy,
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

    public void putRequestInQueue(DeferredConnectionRequest deferredConnectionRequest) {
        try {
            initialize();
            eventQueue.put(deferredConnectionRequest);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    class RequestProcessor extends Thread {
        @Override
        public void run() {
            while (true) {
                DeferredConnectionRequest deferredConnectionRequest = null;
                try {
                    Thread.sleep(5000);
                    deferredConnectionRequest = eventQueue.take();
                    switch (deferredConnectionRequest.getQueueRequestType()) {
                        case CANCEL_USER_RENTAL:
                            ResponseEntity<Rental> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                            try {
                                responseEntity = rentalServiceProxy.
                                        cancelUserRental(deferredConnectionRequest.getUserUid(), deferredConnectionRequest.getRentalUid());
                            } catch (FeignException e) {
                                DeferredConnectionsQueue.this.putRequestInQueue(deferredConnectionRequest);
                            }

                            if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
                                try {
                                    stationServiceProxy.updateLocatedScooterReserve(responseEntity.getBody().getLocated_scooter_uid(), Boolean.TRUE);
                                } catch (FeignException e) {
                                    DeferredConnectionRequest newDeferredConnectionRequest = new DeferredConnectionRequest();
                                    newDeferredConnectionRequest.setUpdateLocatedScooterReserve(responseEntity.getBody().getLocated_scooter_uid(), true);

                                    DeferredConnectionsQueue.this.putRequestInQueue(newDeferredConnectionRequest);
                                }

                                try {
                                    paymentServiceProxy.cancelPayment(responseEntity.getBody().getPayment_uid());
                                } catch (FeignException e) {
                                    DeferredConnectionRequest newDeferredConnectionRequest = new DeferredConnectionRequest();
                                    newDeferredConnectionRequest.setCancelPayment(responseEntity.getBody().getPayment_uid());

                                    DeferredConnectionsQueue.this.putRequestInQueue(newDeferredConnectionRequest);
                                }
                            }
                            break;
                        case FINISH_USER_RENTAL:
                            ResponseEntity<FinishedRentalResponse> responseEntity2 = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                            try {
                                responseEntity2 = rentalServiceProxy.
                                        finishUserRental(deferredConnectionRequest.getUserUid(), deferredConnectionRequest.getRentalUid());
                            } catch (FeignException e) {
                                DeferredConnectionsQueue.this.putRequestInQueue(deferredConnectionRequest);
                            }

                            if (responseEntity2.getStatusCode() != HttpStatus.NOT_FOUND) {
                                try {
                                    stationServiceProxy.updateLocatedScooterReserve(responseEntity2.getBody().getLocatedScooterUid(), Boolean.TRUE);
                                } catch (FeignException e) {
                                    DeferredConnectionRequest newDeferredConnectionRequest = new DeferredConnectionRequest();
                                    newDeferredConnectionRequest.setUpdateLocatedScooterReserve(responseEntity2.getBody().getLocatedScooterUid(),
                                            true);

                                    DeferredConnectionsQueue.this.putRequestInQueue(newDeferredConnectionRequest);
                                }

                                try {
                                    stationServiceProxy.updateLocatedScooterRentalStation(responseEntity2.getBody().getLocatedScooterUid(),
                                            responseEntity2.getBody().getReturnToRentalStationUid());
                                } catch (FeignException e) {
                                    DeferredConnectionRequest newDeferredConnectionRequest = new DeferredConnectionRequest();
                                    newDeferredConnectionRequest.setUpdateLocatedScooterRentalStation(responseEntity2.getBody().getLocatedScooterUid(),
                                            responseEntity2.getBody().getReturnToRentalStationUid());

                                    DeferredConnectionsQueue.this.putRequestInQueue(newDeferredConnectionRequest);
                                }

                                try {
                                    List<Rental> locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(responseEntity2.getBody().getLocatedScooterUid()).getBody();

                                    Rental currentRental = null;
                                    for (Rental rental : locatedScooterRentals) {
                                        if (rental.getRental_uid() == deferredConnectionRequest.getRentalUid()) {
                                            currentRental = rental;
                                        }
                                    }

                                    Rental lastRental = null;
                                    for (Rental rental : locatedScooterRentals) {
                                        if (rental.getRental_uid() != deferredConnectionRequest.getRentalUid()) {
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
                                    DeferredConnectionRequest newDeferredConnectionRequest = new DeferredConnectionRequest();
                                    newDeferredConnectionRequest.setUpdateLocatedScooterCurrentCharge(responseEntity2.getBody().getLocatedScooterUid(),
                                            deferredConnectionRequest.getRentalUid());

                                    DeferredConnectionsQueue.this.putRequestInQueue(newDeferredConnectionRequest);
                                }
                            }
                            break;
                        case UPDATE_SCOOTER_RESERVE:
                            try {
                                stationServiceProxy.updateLocatedScooterReserve(deferredConnectionRequest.getLocatedScooterUid(), deferredConnectionRequest.isAvailability());
                            } catch (FeignException e) {
                                DeferredConnectionsQueue.this.putRequestInQueue(deferredConnectionRequest);
                            }
                            break;
                        case UPDATE_SCOOTER_RENTAL_STATION:
                            try {
                                stationServiceProxy.updateLocatedScooterRentalStation(deferredConnectionRequest.getLocatedScooterUid(),
                                        deferredConnectionRequest.getRentalStationUid());
                            } catch (FeignException e) {
                                DeferredConnectionsQueue.this.putRequestInQueue(deferredConnectionRequest);
                            }
                            break;
                        case UPDATE_SCOOTER_CURRENT_CHARGE:
                            try {
                                List<Rental> locatedScooterRentals = rentalServiceProxy.getLocatedScooterRentals(deferredConnectionRequest.getLocatedScooterUid()).getBody();

                                Rental currentRental = null;
                                for (Rental rental : locatedScooterRentals) {
                                    if (rental.getRental_uid() == deferredConnectionRequest.getRentalUid()) {
                                        currentRental = rental;
                                    }
                                }

                                Rental lastRental = null;
                                for (Rental rental : locatedScooterRentals) {
                                    if (rental.getRental_uid() != deferredConnectionRequest.getRentalUid()) {
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

                                stationServiceProxy.updateLocatedScooterCurrentCharge(deferredConnectionRequest.getLocatedScooterUid(),
                                        currentCharge);
                            } catch (FeignException e) {
                                DeferredConnectionsQueue.this.putRequestInQueue(deferredConnectionRequest);
                            }
                            break;
                        case CANCEL_PAYMENT:
                            try {
                                paymentServiceProxy.cancelPayment(deferredConnectionRequest.getPaymentUid());
                            } catch (FeignException e) {
                                DeferredConnectionsQueue.this.putRequestInQueue(deferredConnectionRequest);
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
