package com.example.pet_shop.model.DTOS;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {

    private int id;
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private BigDecimal turnover;
    private BigDecimal personalDiscount;
    private String townName;
    private String address;
    private LocalDateTime createdAt;
    private boolean isSubscribed;
    private boolean isAdmin;


}