package com.example.pet_shop.model.DTOS.productDTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductInfoDTO {

    private int id;
    private String name;
    private String description;
    private String supplier;
    private String subcategory;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discount;
}
