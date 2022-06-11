package com.example.rsoi_course_work.gateway_service.model;

import java.util.Objects;
import java.util.UUID;

public class PaymentInfo {
    private UUID paymentUid;
    private PaymentStatus status;
    private Integer price;

    public PaymentInfo() {
    }

    public PaymentInfo(UUID paymentUid, PaymentStatus status, Integer price) {
        this.paymentUid = paymentUid;
        this.status = status;
        this.price = price;
    }

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public void setPaymentUid(UUID paymentUid) {
        this.paymentUid = paymentUid;
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
        if (!(o instanceof PaymentInfo)) return false;

        PaymentInfo that = (PaymentInfo) o;

        if (!Objects.equals(paymentUid, that.paymentUid)) return false;
        if (status != that.status) return false;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = paymentUid != null ? paymentUid.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "payment_uid=" + paymentUid +
                ", status=" + status +
                ", price=" + price +
                '}';
    }
}
