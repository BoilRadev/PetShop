package com.example.pet_shop.model.DTOS.orderDTO;

import com.example.pet_shop.model.entities.Product;
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
    private Double grossValue;
    private Double discountAmount;
    private Map<Product , Integer> userCart;
    private boolean isPaid;
}
