package com.example.pet_shop.service;


import com.example.pet_shop.model.DTOS.OrderInfoDTO;
import com.example.pet_shop.model.DTOS.OrderStatusDTO;
import com.example.pet_shop.model.entities.Discount;
import com.example.pet_shop.model.entities.Order;
import com.example.pet_shop.model.entities.OrderStatus;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;

  public OrderInfoDTO addToCart(OrderInfoDTO dto) {


        return null;

    }

    public OrderInfoDTO removeFromCart(OrderInfoDTO dto) {
        return null;
    }

    public void editStatus(int id) {
        Optional<Order> opt = orderRepository.findById(id);
        if(!opt.isPresent()){
            throw new BadRequestException("No order found with id: " + id);
        }

        Order o = opt.get();
        o.setOrderStatus(o.getOrderStatus());
        orderRepository.save(o);
    }

    public OrderStatus getStatus(int id) {
        Optional<Order> opt = orderRepository.findById(id);
        if(!opt.isPresent()){
            throw new BadRequestException("No order found with id: " + id);
        }
        Order o = opt.get();
        return o.getOrderStatus();
    }
}
