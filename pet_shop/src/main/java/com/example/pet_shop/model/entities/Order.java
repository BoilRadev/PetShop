package com.example.pet_shop.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
    @Getter
    @Entity(name = "orders")
    public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "status_id")
        private OrderStatus orderStatus;

        @ManyToOne
        @JoinColumn(name = "payment_method_id")
        private PaymentMethod paymentMethod;

        @Column
        private String address;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "gross_value")
        private BigDecimal grossValue;

        @Column(name = "discount_amount")
        private BigDecimal discountAmount;

        @Column(name = "net_value")
        private BigDecimal netValue;

        @Column(name = "is_paid")
        private boolean isPaid;

    }
