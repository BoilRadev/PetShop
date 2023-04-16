package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.DiscountInfoDTO;
import com.example.pet_shop.model.DTOS.productDTOs.ProductInfoDTO;
import com.example.pet_shop.model.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DiscountService extends AbstractService {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private DiscountRepository discountRepository;

    public DiscountInfoDTO addDiscount(ProductInfoDTO dto) {
        return null;
    }

    public DiscountInfoDTO editDiscount(int id) {
        return null;
    }
}
