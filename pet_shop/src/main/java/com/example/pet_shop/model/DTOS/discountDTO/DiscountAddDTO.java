package com.example.pet_shop.model.DTOS.discountDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DiscountAddDTO {

    private BigDecimal percent;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private boolean isActive;
}
