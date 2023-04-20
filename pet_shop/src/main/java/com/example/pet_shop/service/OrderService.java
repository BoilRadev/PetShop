package com.example.pet_shop.service;


import com.example.pet_shop.model.DTOS.*;
import com.example.pet_shop.model.entities.Order;
import com.example.pet_shop.model.entities.OrderStatus;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.OrderRepository;
import com.example.pet_shop.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

  public CartDTO addToCart(AddToCartDTO dto, CartDTO cart) {

      Product product = productRepository.getProductByName(dto.getName()).orElseThrow(() -> new NotFoundException("Nfasfd"));
      if (!cart.getCart().containsKey(product)){
          cart.getCart().put(product , 1 );
      }
      cart.getCart().put(product,cart.getCart().get(product)+1);

        return cart;

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

    public ViewCartDTO viewCart(Map<Product, Integer> cart) {

        ViewCartDTO vcDTO = new ViewCartDTO();
        ViewProductCartDTO viewProductCartDTO = new ViewProductCartDTO();
        for (Product p : cart.keySet()) {
            viewProductCartDTO.setName(p.getName());
            viewProductCartDTO.setPrice(p.getPrice());
            viewProductCartDTO.setQuantity(cart.get(p));
            vcDTO.getCheckCart().add(viewProductCartDTO);
        }

        vcDTO.setGrossValue( vcDTO.getCheckCart().stream()
                .map(p -> p.getPrice())
                .reduce(BigDecimal::add )
                .get());
        return vcDTO;
    }
}
