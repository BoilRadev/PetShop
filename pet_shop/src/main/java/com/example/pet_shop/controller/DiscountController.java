package com.example.pet_shop.controller;


import com.example.pet_shop.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscountController extends AbstractController {

    @Autowired
    private DiscountService discountService;


    public DiscountService getDiscountService() {
        return discountService;
    }
}
