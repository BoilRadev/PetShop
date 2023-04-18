package com.example.pet_shop.service;


import com.example.pet_shop.model.DTOS.OrderInfoDTO;
import com.example.pet_shop.model.DTOS.OrderStatusDTO;
import com.example.pet_shop.model.entities.Discount;
import com.example.pet_shop.model.entities.Order;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;
/*
    public OrderInfoDTO addToCart(OrderInfoDTO dto) {
        Order order = mapper.map(dto,Order.class);
            order.setUser(dto.getUser());
            order.setOrderStatus(dto.getDescription());
            order.setPaymentMethod(dto.getPaymentMethodId());
            order.setCreatedAt(LocalDateTime.now());
            order.setGrossValue(dto.getGrossValue());
            order.setDiscountAmount(dto.getDiscountAmount());
            order.setPaid(dto.isPaid());
            orderRepository.save(order);

        return mapper.map(order, OrderInfoDTO.class);

    }
*/
    public OrderInfoDTO removeFromCart(OrderInfoDTO dto) {
        return null;
    }

    public OrderStatusDTO editStatus(int id) {
        return null;
    }

    public OrderStatusDTO getStatus(int id) {
        Optional<Order> opt = orderRepository.findById(id);
        if(!opt.isPresent()){
            throw new BadRequestException("No order found with id: " + id);
        }
        return null;
    }
}
