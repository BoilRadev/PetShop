package com.example.pet_shop.model.DTOS.orderDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ViewProductCartDTO {

    private String name;
    private int quantity;
    private BigDecimal price;
}
