package com.example.pet_shop.model.DTOS.productDTOs;


import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.model.entities.Supplier;
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
    private Supplier supplier;
    private Subcategory subcategory;
    private Category category;
    private int quantity;
    private BigDecimal price;

}
