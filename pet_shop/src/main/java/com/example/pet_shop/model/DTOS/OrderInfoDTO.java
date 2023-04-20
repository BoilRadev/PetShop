package com.example.pet_shop.model.DTOS;

import com.example.pet_shop.model.entities.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

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
    private Map<Product , Integer> userCart;
    private boolean isPaid;
}
