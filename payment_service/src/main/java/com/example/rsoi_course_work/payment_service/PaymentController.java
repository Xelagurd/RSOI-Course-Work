package com.example.rsoi_course_work.payment_service;

import com.example.rsoi_course_work.payment_service.model.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payments/{paymentUid}")
    public ResponseEntity<Payment> getPayment(@PathVariable("paymentUid") UUID paymentUid) {
        return paymentService.getPayment(paymentUid);
    }

    @DeleteMapping("/payments/{paymentUid}")
    public ResponseEntity<HttpStatus> cancelPayment(@PathVariable("paymentUid") UUID paymentUid) {
        return paymentService.cancelPayment(paymentUid);
    }

    @PostMapping("/payments")
    public ResponseEntity<HttpStatus> createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }
}
