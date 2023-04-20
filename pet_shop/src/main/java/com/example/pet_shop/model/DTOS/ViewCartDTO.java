package com.example.pet_shop.model.DTOS;

import com.example.pet_shop.model.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ViewCartDTO {

    private List<ViewProductCartDTO> checkCart = new ArrayList<>();
    private BigDecimal grossValue;


}
