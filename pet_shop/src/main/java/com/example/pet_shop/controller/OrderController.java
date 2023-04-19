package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.CartDTO;
import com.example.pet_shop.model.DTOS.OrderInfoDTO;
import com.example.pet_shop.model.DTOS.OrderStatusDTO;
import com.example.pet_shop.model.DTOS.productDTOs.ProductInfoDTO;
import com.example.pet_shop.model.entities.OrderStatus;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.service.OrderService;
import com.example.pet_shop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@RestController
public class OrderController extends AbstractController {

    @Autowired
    private OrderService orderService;

//    @PostMapping("/orders")
//    public OrderInfoDTO addToCart(@RequestBody Product product, HttpSession session){
//      Map<Product , Integer> cart = session.getAttribute(cart);
//        return orderService.addToCart(product);
//    }

    @DeleteMapping("/orders")
    public OrderInfoDTO removeFromCart(@RequestBody OrderInfoDTO dto){
        return orderService.removeFromCart(dto);
    }

    @PutMapping("/orders/{id}/status")
    public void editStatus(@PathVariable int id){
         orderService.editStatus(id);
    }

    @GetMapping("/orders/{id}/status")
    public OrderStatus getStatus(@PathVariable int id){
        return orderService.getStatus(id);
    }

}
