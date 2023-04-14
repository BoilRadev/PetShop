package com.example.pet_shop.model.DTOS;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RegisterDTO (int id , String email , String password, String confirmPassword, String firstName ,
                           String lastName, String phoneNumber, BigDecimal personalDiscount, String town,
                           String address , LocalDateTime createdAt, boolean isSubscribed, boolean isAdmin) {
    }