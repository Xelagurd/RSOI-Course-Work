package com.example.rsoi_course_work.gateway_service;

import com.example.rsoi_course_work.gateway_service.model.PairOfScooterUidAndPaymentUid;
import com.example.rsoi_course_work.gateway_service.proxy.ScooterServiceProxy;
import com.example.rsoi_course_work.gateway_service.proxy.PaymentServiceProxy;
import com.example.rsoi_course_work.gateway_service.proxy.RentalServiceProxy;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class QueueService {
    private static BlockingQueue<QueueRequest> eventQueue = null;

    private final ScooterServiceProxy scooterServiceProxy;
    private final RentalServiceProxy rentalServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;

    public QueueService(ScooterServiceProxy scooterServiceProxy, RentalServiceProxy rentalServiceProxy,
                        PaymentServiceProxy paymentServiceProxy) {
        this.scooterServiceProxy = scooterServiceProxy;
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
                            ResponseEntity<PairOfScooterUidAndPaymentUid> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                            try {
                                responseEntity = rentalServiceProxy.
                                        cancelUserRental(queueRequest.getUsername(), queueRequest.getRentalUid());
                            } catch (FeignException e) {
                                QueueService.this.putRequestInQueue(queueRequest);
                            }

                            if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
                                try {
                                    scooterServiceProxy.updateScooterReserve(responseEntity.getBody().getScooterUid(), true);
                                } catch (FeignException e) {
                                    QueueService.this.putRequestInQueue(queueRequest);
                                }

                                try {
                                    paymentServiceProxy.cancelPayment(responseEntity.getBody().getPaymentUid());
                                } catch (FeignException e) {
                                    QueueService.this.putRequestInQueue(queueRequest);
                                }
                            }
                            break;
                        case FINISH_USER_RENTAL:
                            ResponseEntity<UUID> responseEntity2 = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                            try {
                                responseEntity2 = rentalServiceProxy.
                                        finishUserRental(queueRequest.getUsername(), queueRequest.getRentalUid());
                            } catch (FeignException e) {
                                QueueService.this.putRequestInQueue(queueRequest);
                            }

                            if (responseEntity2.getStatusCode() != HttpStatus.NOT_FOUND) {
                                try {
                                    scooterServiceProxy.updateScooterReserve(responseEntity2.getBody(), true);
                                } catch (FeignException e) {
                                    QueueService.this.putRequestInQueue(queueRequest);
                                }
                            }
                            break;
                        case UPDATE_SCOOTER_RESERVE:
                            try {
                                scooterServiceProxy.updateScooterReserve(queueRequest.getScooterUid(), queueRequest.isAvailability());
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
