package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.payment.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "payment-service", url = "https://payment-service-xelagurd.herokuapp.com/api/v1")
public interface PaymentServiceProxy {

    @GetMapping("/payments/{paymentUid}")
    public ResponseEntity<Payment> getPayment(@PathVariable("paymentUid") UUID paymentUid);

    @DeleteMapping("/payments/{paymentUid}")
    public ResponseEntity<HttpStatus> cancelPayment(@PathVariable("paymentUid") UUID paymentUid);

    @PostMapping("/payments")
    public ResponseEntity<HttpStatus> createPayment(@RequestBody Payment payment);
}
