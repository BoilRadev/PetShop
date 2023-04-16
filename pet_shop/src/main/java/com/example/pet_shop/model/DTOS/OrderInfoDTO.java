package com.example.pet_shop.model.DTOS;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderInfoDTO {

    private int userId;
    private int statusId;
    private int paymentMethodId;
    private LocalDateTime createdAt;
    private BigDecimal grossValue;
    private BigDecimal discountAmount;
    private boolean isPaid;
}
