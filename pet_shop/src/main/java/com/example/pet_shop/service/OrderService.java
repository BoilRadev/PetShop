package com.example.pet_shop.service;


import com.example.pet_shop.model.DTOS.*;
import com.example.pet_shop.model.entities.*;
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

        if (product.getQuantity() > 0) {
            if (!cart.getCart().containsKey(product)) {
                cart.getCart().put(product, 1);
            }
            cart.getCart().put(product, cart.getCart().get(product) + 1);
        } else {
            throw new NotFoundException("Not enough products.");
        }
        return cart;
    }


    public void removeFromCart(int productId, CartDTO cart)  {

        Product product = productRepository.getProductsById(productId).orElseThrow(()
                -> new NotFoundException("Product not found"));

        if (cart.getCart().containsKey(product)) {
            if (cart.getCart().get(product) > 1) {
                cart.getCart().put(product,cart.getCart().get(product) - 1);
            } else {
                cart.getCart().remove(product);
            }
        }
    }

    public void editStatus(int id) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isEmpty()) {
            throw new BadRequestException("No order found with id: " + id);
        }

        Order o = opt.get();
        o.setOrderStatus(o.getOrderStatus());
        orderRepository.save(o);
    }

    public OrderStatus getStatus(int id) {
        Optional<Order> opt = orderRepository.findById(id);
        if (!opt.isPresent()) {
            throw new BadRequestException("No order found with id: " + id);
        }
        Order o = opt.get();
        return o.getOrderStatus();
    }

    public ViewCartDTO viewCart(Map<Product, Integer> cart) {
        ViewCartDTO vcDTO = new ViewCartDTO();
        BigDecimal totalGrossValue = BigDecimal.ZERO;

        for (Product p : cart.keySet()) {
            ViewProductCartDTO viewProductCartDTO = new ViewProductCartDTO();
            viewProductCartDTO.setName(p.getName());
            viewProductCartDTO.setPrice(p.getPrice());
            viewProductCartDTO.setQuantity(cart.get(p));

            vcDTO.getCheckCart().add(viewProductCartDTO);
            totalGrossValue = totalGrossValue.add(p.getPrice().multiply(BigDecimal.valueOf(cart.get(p))));
        }

        vcDTO.setGrossValue(totalGrossValue);
        return vcDTO;
    }
}
