package com.example.pet_shop.model.DTOS.productDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductEditRequestDTO {

    private String name;
    private String description;
    private String supplier;
    private String subcategoryId;
    private int quantity;
    private double price;

}