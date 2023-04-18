package com.example.pet_shop.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "orders")
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
        private LocalDateTime createdAt;

        @Column
        private BigDecimal grossValue;

        @Column(name = "discount_amount")
        private BigDecimal discountAmount;

        @Column
        private BigDecimal netValue;

        @Column
        private boolean isPaid;

    }
