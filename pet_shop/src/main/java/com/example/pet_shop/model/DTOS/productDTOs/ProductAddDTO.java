package com.example.pet_shop.model.DTOS.productDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductAddDTO {

    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;

}
