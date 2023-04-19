package com.example.pet_shop.service;


import com.example.pet_shop.model.DTOS.OrderInfoDTO;
import com.example.pet_shop.model.entities.Order;
import com.example.pet_shop.model.entities.OrderStatus;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;


//  public OrderInfoDTO addToCart(Product product) {
//      Map<Product, Integer> cart = session.getAtributte()
//      if (!cart.containsKey(product.getName())){
//          cart.put(product.getName()) , new LinkedList<>();
//      }
//      cart.get(product.getName()).add(product);
//
//        return null;
//
//    }
//public OrderInfoDTO addToCart(AddToCartDTO productInfo) {
//    Product product = getProductById(productInfo.getId());
//    int quantity = productInfo.getQuantity();
//
//    // Get the user's cart or create a new one if it doesn't exist
//    Cart cart = getOrCreateCartForUser(currentUser);
//
//    // Add the product and quantity to the cart
//    cart.addProduct(product, quantity);
//
//    // Create and return an OrderInfoDTO object representing the updated cart
//    OrderInfoDTO orderInfo = new OrderInfoDTO(cart);
//    return orderInfo;
//}

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
