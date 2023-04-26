package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAllByUserId(int id, Pageable page);
    Order findOrderByUserId(int id);

    Order getById(int id);
}
