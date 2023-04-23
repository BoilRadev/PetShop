package com.example.pet_shop.service;


import com.example.pet_shop.controller.Logger;
import com.example.pet_shop.model.DTOS.OrderPayDTO;
import com.example.pet_shop.model.DTOS.orderDTO.CartDTO;
import com.example.pet_shop.model.DTOS.orderDTO.PaymentRequest;
import com.example.pet_shop.model.DTOS.orderDTO.ViewCartDTO;
import com.example.pet_shop.model.DTOS.orderDTO.ViewProductCartDTO;
import com.example.pet_shop.model.DTOS.productDTOs.ProductInfoDTO;
import com.example.pet_shop.model.entities.*;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.OrderRepository;
import com.example.pet_shop.model.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public void addToCart(int productId, CartDTO cart) {

        ProductInfoDTO product = mapper.map(productRepository.getProductsById(productId).orElseThrow(()
                -> new NotFoundException("Product not found")), ProductInfoDTO.class);

        if (product.getQuantity() > 0) {
            if (!cart.getCart().containsKey(product)) {
                cart.getCart().put(product, 1);
            }
            else {
                cart.getCart().put(product, cart.getCart().get(product) + 1);
            }
        } else {
            throw new NotFoundException("Not enough products.");
        }

    }
//    public void createOrder(Logger logger, CartDTO cart, @Valid OrderPayDTO dto) {
//        System.out.println("starting");
//        Order order = Order.builder()
//                .user(userRepository.getUserById(logger.id()))
//                .paymentMethod(paymentMethodRepository.findById(dto.getPaymentMethodId()).get())
//                .orderStatus(orderStatusRepository.findByType("CONFIRMED"))
//                .address(userRepository.getUserById(logger.id()).getAddress())
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        BigDecimal grossValue = cart.getCart().entrySet().stream()
//                .map(entry -> {
//                    ProductInfoDTO product = entry.getKey();
//                    int quantity = entry.getValue();
//                    BigDecimal productPrice = product.getPrice();
//
//                    if (product.getDiscount() != null) {
//                        Product theProduct = productRepository.getProductsById(product.getId()).get();
//                        BigDecimal discountPercent = theProduct.getDiscount().getPercent();
//                        productPrice = productPrice.multiply(BigDecimal.ONE.subtract(discountPercent.divide(BigDecimal.valueOf(100))));
//                    }
//
//                    return productPrice.multiply(BigDecimal.valueOf(quantity));
//                })
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal discountAmount = cart.getCart().entrySet().stream()
//                .filter(entry -> entry.getKey().getDiscount() != null)
//                .map(entry -> {
//                    BigDecimal productValue = entry.getKey().getPrice()
//                            .multiply(BigDecimal.valueOf(entry.getValue()));
//                    Product theProduct1 = productRepository.getProductsById(entry.getKey().getId()).get();
//                    BigDecimal discountPercent = theProduct1.getDiscount().getPercent();
//                    return productValue.multiply(discountPercent.divide(BigDecimal.valueOf(100)));
//                })
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal netValue = grossValue.subtract(discountAmount);
//
//        order.setGrossValue(grossValue);
//        order.setDiscountAmount(discountAmount);
//        order.setNetValue(netValue);
//        order.setPaid(false);
//
//        orderRepository.save(order);
//    }

    public void removeFromCart(int productId, CartDTO cart)  {

        ProductInfoDTO product = mapper.map(productRepository.getProductsById(productId).orElseThrow(()
                -> new NotFoundException("Product not found")),ProductInfoDTO.class);

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

    public OrderStatus getStatus(int id, Logger logger) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isEmpty()) {
            throw new BadRequestException("No order found with id: " + id);
        }
        if(logger.id() != orderRepository.findOrderByUserId(logger.id()).getId()){
            throw new BadRequestException("You can see only your own order status.");
        }
        Order o = opt.get();
        return o.getOrderStatus();
    }

    public ViewCartDTO viewCart(Map<ProductInfoDTO, Integer> cart) {
        ViewCartDTO vcDTO = new ViewCartDTO();
        double totalGrossValue = 0;

        for (ProductInfoDTO p : cart.keySet()) {
            ViewProductCartDTO viewProductCartDTO = new ViewProductCartDTO();
            viewProductCartDTO.setName(p.getName());
            viewProductCartDTO.setPrice(p.getPrice());
            viewProductCartDTO.setQuantity(cart.get(p));

            vcDTO.getCheckCart().add(viewProductCartDTO);
            totalGrossValue = totalGrossValue + (p.getPrice() * (cart.get(p)));
        }

        vcDTO.setGrossValue(totalGrossValue);
        return vcDTO;
    }
    private Order getOrderById(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new NotFoundException("Order not found.");
        }
        return optionalOrder.get();
    }
    public void payOrder(PaymentRequest pm, Logger logger) {

        if (logger.id() != orderRepository.findById(pm.getOrderId()).orElseThrow(() -> new NotFoundException("Order not found")).getUser().getId()) {
            throw new BadRequestException("You can pay only your own orders.");
        }

        Order order = getOrderById(pm.getOrderId());
        Payment payment = new Payment();
        payment.setUser(userRepository.getUserById(logger.id()));

        payment.setOrder(order);
        payment.setAmount(order.getGrossValue());
        payment.setCreatedAt(order.getCreatedAt());
        payment.setProcessedAt(LocalDateTime.now());
        payment.setTransactionId("All good.");
        payment.setStatus("PAID");

        // update netValue of the order
        double newNetValue = order.getNetValue()/(payment.getAmount());
        order.setNetValue(newNetValue);

        order.setOrderStatus(orderStatusRepository.findByType("PAID"));
        order.setPaid(true);

        orderRepository.save(order);
        paymentRepository.save(payment);
    }
}
