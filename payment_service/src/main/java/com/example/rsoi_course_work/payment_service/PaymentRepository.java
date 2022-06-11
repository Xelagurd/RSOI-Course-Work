package com.example.rsoi_course_work.payment_service;

import com.example.rsoi_course_work.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p from Payment p where p.payment_uid = ?1")
    Optional<Payment> findByPayment_uid(UUID payment_uid);
}