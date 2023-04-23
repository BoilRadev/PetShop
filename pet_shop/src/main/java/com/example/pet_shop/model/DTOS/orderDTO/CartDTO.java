package com.example.pet_shop.model.DTOS.orderDTO;

import com.example.pet_shop.model.DTOS.productDTOs.ProductInfoDTO;
import com.example.pet_shop.model.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartDTO {

    private Map<ProductInfoDTO, Integer> cart = new HashMap<>();
}
