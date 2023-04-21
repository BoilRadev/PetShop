package com.example.pet_shop.model.DTOS.orderDTO;

import com.example.pet_shop.model.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {

    private Map<Product , Integer> cart = new HashMap<>();
}
