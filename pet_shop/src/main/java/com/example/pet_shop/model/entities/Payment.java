package com.example.pet_shop.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;

    @Column
    private LocalDateTime createdAt;

    @Column
    private BigDecimal amount;

    @Column(name = "transaction_id")
    private String description;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column
    private String status;


}
