package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.CartDTO;
import com.example.pet_shop.model.DTOS.OrderStatusDTO;
import com.example.pet_shop.model.DTOS.ProductInfoDTO;
import com.example.pet_shop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;

    @PostMapping("/orders")
    public CartDTO addToCart(@RequestBody ProductInfoDTO dto){
        return productService.addToCart(dto);
    }

    @DeleteMapping("/orders")
    public CartDTO removeFromCart(@RequestBody ProductInfoDTO dto){
        return productService.removeFromCart(dto);
    }

    @PutMapping("/orders/{id}/status")
    public OrderStatusDTO editStatus(@PathVariable int id){
        return productService.editStatus(id);
    }

    @GetMapping("/orders/{id}/status")
    public OrderStatusDTO getStatus(@PathVariable int id){
        return productService.getStatus(id);
    }



}
