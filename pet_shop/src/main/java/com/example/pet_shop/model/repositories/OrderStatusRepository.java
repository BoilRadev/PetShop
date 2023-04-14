package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderRepository, Integer> {
}
