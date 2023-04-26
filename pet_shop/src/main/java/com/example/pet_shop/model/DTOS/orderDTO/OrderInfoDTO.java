package com.example.pet_shop.model.DTOS.orderDTO;

import com.example.pet_shop.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {

    private int orderId;
    private String statusType;
    private BigDecimal discountAmount;
    private BigDecimal grossValue;
    private boolean isPaid;
}
