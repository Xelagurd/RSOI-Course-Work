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

    @Column(nullable = false)
    private UUID payment_uid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @Column(nullable = false)
    private Integer price;

    public Payment() {
    }

    public Payment(Long id, UUID payment_uid, PaymentStatus status, Integer price) {
        super();
        this.id = id;
        this.payment_uid = payment_uid;
        this.status = status;
        this.price = price;
    }

    public Payment(UUID payment_uid, PaymentStatus status, Integer price) {
        super();
        this.payment_uid = payment_uid;
        this.status = status;
        this.price = price;
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

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        if (!Objects.equals(id, payment.id)) return false;
        if (!Objects.equals(payment_uid, payment.payment_uid)) return false;
        if (status != payment.status) return false;
        return Objects.equals(price, payment.price);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (payment_uid != null ? payment_uid.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", payment_uid=" + payment_uid +
                ", status=" + status +
                ", price=" + price +
                '}';
    }
}
