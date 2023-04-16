package com.example.pet_shop.controller;


import com.example.pet_shop.model.DTOS.DiscountAddDTO;
import com.example.pet_shop.model.DTOS.DiscountInfoDTO;

import com.example.pet_shop.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiscountController extends AbstractController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/discounts")
    public DiscountInfoDTO addDiscount(@RequestBody DiscountAddDTO dto){
        return discountService.addDiscount(dto);
    }

    @PutMapping("/discounts/{id}")
    public DiscountInfoDTO editDiscount(@PathVariable int id){
        return discountService.editDiscount(id);
    }

    @DeleteMapping("/discounts/{id}")
    public void deleteDiscount(@PathVariable int id){
    }
}
