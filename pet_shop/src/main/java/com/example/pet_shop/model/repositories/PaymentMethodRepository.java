package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Integer> {
    
}
