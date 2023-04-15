package com.example.pet_shop.controller;


import com.example.pet_shop.model.DTOS.DiscountInfoDTO;
import com.example.pet_shop.model.DTOS.ProductInfoDTO;
import com.example.pet_shop.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiscountController extends AbstractController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/discounts")
    public DiscountInfoDTO addProduct(@RequestBody ProductInfoDTO dto){
        return discountService.addDiscount(dto);
    }

    @PutMapping("/discounts/{id}")
    public DiscountInfoDTO editProduct(@PathVariable int id){
        return discountService.editDiscount(id);
    }

    @DeleteMapping("/discounts/{id}")
    public void deleteProduct(@PathVariable int id){
    }
}
