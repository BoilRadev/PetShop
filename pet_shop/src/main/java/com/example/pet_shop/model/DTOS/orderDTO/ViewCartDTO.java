package com.example.pet_shop.model.DTOS.orderDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ViewCartDTO {

    private List<ViewProductCartDTO> checkCart = new ArrayList<>();
    private Double grossValue;


}
