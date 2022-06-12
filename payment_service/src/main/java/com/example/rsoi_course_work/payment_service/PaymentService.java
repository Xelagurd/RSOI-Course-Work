package com.example.rsoi_course_work.payment_service;

import com.example.rsoi_course_work.payment_service.exception.ErrorResponse;
import com.example.rsoi_course_work.payment_service.model.Payment;
import com.example.rsoi_course_work.payment_service.model.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public ResponseEntity<Payment> getPayment(UUID paymentUid) {
        Payment payment = paymentRepository.findByPayment_uid(paymentUid).orElseThrow(() ->
                new ErrorResponse("Not found payment for UID"));

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> cancelPayment(UUID paymentUid) {
        Payment payment = paymentRepository.findByPayment_uid(paymentUid).orElseThrow(() ->
                new ErrorResponse("Not found payment for UID"));

        payment.setStatus(PaymentStatus.CANCELED);
        paymentRepository.save(payment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> createPayment(Payment payment) {
        paymentRepository.save(new Payment(payment.getPayment_uid(), payment.getPrice(), payment.getStatus()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}