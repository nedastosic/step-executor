package com.neda.stepexecutor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Transaction {
    private @Id
    @GeneratedValue
    Long id;
    private double price;
    private Timestamp timestamp;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    @Lazy
    private Step step;

    public Transaction() {
    }

    public Transaction(double price, Timestamp timestamp, String status, Step step) {
        this.price = price;
        this.timestamp = timestamp;
        this.status = status;
        this.step = step;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", price=" + price +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
