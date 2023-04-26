package com.example.pet_shop.model.DTOS.discountDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountAddDTO {

    private Double percent;
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;
    private boolean isActive;
    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return now.isEqual(fromDate) || (now.isAfter(fromDate) && now.isBefore(toDate));
    }
}
