package com.example.pet_shop.controller;

import com.example.pet_shop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/orders/cart")
    public somethingDTO addToCart(@RequestBody )

}
