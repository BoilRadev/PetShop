package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.CartDTO;
import com.example.pet_shop.model.DTOS.OrderStatusDTO;
import com.example.pet_shop.model.DTOS.ProductInfoDTO;
import com.example.pet_shop.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private ProductRepository productRepository;

    public CartDTO addToCart(ProductInfoDTO dto) {
        return new CartDTO();
    }

    public CartDTO removeFromCart(ProductInfoDTO dto) {
        return new CartDTO();
    }

    public OrderStatusDTO editStatus(int id) {
        return new OrderStatusDTO();

    }

    public OrderStatusDTO getStatus(int id) {
        return new OrderStatusDTO();
    }

}
