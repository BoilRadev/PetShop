package com.example.pet_shop.model.DTOS.productDTOs;

import com.example.pet_shop.model.entities.Subcategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductEditRequestDTO {

    private String name;
    private String description;
    private int supplierId;
    private int subcategoryId;
    private int quantity;
    private BigDecimal price;

}