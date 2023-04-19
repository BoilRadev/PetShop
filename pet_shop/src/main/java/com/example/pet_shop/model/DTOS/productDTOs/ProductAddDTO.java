package com.example.pet_shop.model.DTOS.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddDTO {

    private String name;
    private String description;
    private int supplierId;
    private int subcategoryId;
    private int categoryId;
    private int quantity;
    private BigDecimal price;
}
