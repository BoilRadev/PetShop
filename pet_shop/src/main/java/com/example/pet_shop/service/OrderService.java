package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.CartDTO;
import com.example.pet_shop.model.DTOS.OrderInfoDTO;
import com.example.pet_shop.model.DTOS.OrderStatusDTO;
import com.example.pet_shop.model.DTOS.ProductInfoDTO;
import com.example.pet_shop.model.entities.Order;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.model.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderInfoDTO addToCart(OrderInfoDTO dto) {
        Order order = mapper.map(dto,Order.class);
        //admin verification??

        if(!user.isAdmin()){
            throw new UnauthorizedException("You are not admin");
        }
        else {
            order.setUser(dto.getUser());
            order.setOrderStatus(dto.getDescription());
            order.setPaymentMethod(dto.getPaymentMethodId()));
            order.setCreatedAt(LocalDateTime.now());
            order.setGrossValue(dto.getGrossValue());
            order.setDiscountAmount(dto.getDiscountAmount());
            order.setPaid(dto.isPaid());
            orderRepository.save(order);
        }
        return mapper.map(order, OrderInfoDTO.class);    }

    public OrderInfoDTO removeFromCart(OrderInfoDTO dto) {
        return new OrderInfoDTO();
    }

    public OrderStatusDTO editStatus(int id) {
        return new OrderStatusDTO();

    }

    public OrderStatusDTO getStatus(int id) {
        return new OrderStatusDTO();
    }
}
