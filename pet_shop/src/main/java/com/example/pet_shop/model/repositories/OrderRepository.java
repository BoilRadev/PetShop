package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Product;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
