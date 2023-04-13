package com.example.pet_shop.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity(name = "posts")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "created_at")
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
