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

      Product product = productRepository.getProductsById(dto.getProductId()).orElseThrow(()
              -> new NotFoundException("Product not found"));

      if(product.getQuantity() > 0) {
          if (!cart.getCart().containsKey(product)) {
              cart.getCart().put(product, 1);
          }
          cart.getCart().put(product, cart.getCart().get(product) + 1);
          }
      return cart;
    }


    public CartDTO removeFromCart(AddToCartDTO dto,CartDTO cart)  {

        Product product = productRepository.getProductsById(dto.getProductId()).orElseThrow(()
                -> new NotFoundException("Product not found"));

        if (cart.getCart().containsKey(product)) {
            int currentQuantity = cart.getCart().get(product);
            if (currentQuantity > 1) {
                cart.getCart().put(product, currentQuantity - 1);
            } else {
                cart.getCart().remove(product);
            }
        } else {
            throw new NotFoundException("Product not found in cart");
        }

        return cart;
    }

    public void editStatus(int id) {
        Optional<Order> opt = orderRepository.findById(id);
        if(opt.isEmpty()){
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
