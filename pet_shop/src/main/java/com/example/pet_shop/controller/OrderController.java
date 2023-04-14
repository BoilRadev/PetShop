package com.example.pet_shop.controller;

import com.example.pet_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController extends AbstractController {

    @Autowired
    private OrderService orderService;


}
