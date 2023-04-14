package com.example.pet_shop.service;

import com.example.pet_shop.model.repositories.ProductRepository;
import com.example.pet_shop.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ProductService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ProductRepository productRepository;
}
