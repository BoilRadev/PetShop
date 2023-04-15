package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderRepository, Integer> {

}
