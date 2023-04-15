package com.example.pet_shop.service;

import com.example.pet_shop.model.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private OrderRepository orderRepository;
}
