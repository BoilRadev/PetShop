package com.example.pet_shop.model.DTOS.productDTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterDTO  {

    private String category;
    private String subcategory;
}
