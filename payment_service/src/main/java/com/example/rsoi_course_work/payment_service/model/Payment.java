package com.example.rsoi_course_work.payment_service.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "payments", schema = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID payment_uid;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(Long id, UUID payment_uid, Integer price, PaymentStatus status) {
        this.id = id;
        this.payment_uid = payment_uid;
        this.price = price;
        this.status = status;
    }

    public Payment(UUID payment_uid, Integer price, PaymentStatus status) {
        this.payment_uid = payment_uid;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(UUID payment_uid) {
        this.payment_uid = payment_uid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (!Objects.equals(id, payment.id)) return false;
        if (!Objects.equals(payment_uid, payment.payment_uid)) return false;
        if (!Objects.equals(price, payment.price)) return false;
        return status == payment.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (payment_uid != null ? payment_uid.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", payment_uid=" + payment_uid +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
