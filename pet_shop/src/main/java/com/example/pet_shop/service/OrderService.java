package com.example.pet_shop.service;


import com.example.pet_shop.controller.Logger;
import com.example.pet_shop.model.DTOS.OrderPayDTO;
import com.example.pet_shop.model.DTOS.orderDTO.CartDTO;
import com.example.pet_shop.model.DTOS.orderDTO.PaymentRequest;
import com.example.pet_shop.model.DTOS.orderDTO.ViewCartDTO;
import com.example.pet_shop.model.DTOS.orderDTO.ViewProductCartDTO;
import com.example.pet_shop.model.entities.*;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.OrderRepository;
import com.example.pet_shop.model.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public void addToCart(int productId, CartDTO cart) {

        Product product = productRepository.getProductsById(productId).orElseThrow(()
                -> new NotFoundException("Product not found"));

        if (product.getQuantity() > 0) {
            if (!cart.getCart().containsKey(product)) {
                cart.getCart().put(product, 1);
            }else {
                cart.getCart().put(product, cart.getCart().get(product) + 1);
            }

        } else {
            throw new NotFoundException("Not enough products.");
        }
    }


    public void createOrder(Logger logger, CartDTO cart, @Valid OrderPayDTO dto) {
        Order order = new Order();
        order.setUser(userRepository.getUserById(logger.id()));
        order.setPaymentMethod(paymentMethodRepository.findById(dto.getPaymentMethodId()).get());
        order.setOrderStatus(getOrderStatus("ACCEPTED"));
        order.setAddress(userRepository.getUserById(logger.id()).getAddress());
        order.setCreatedAt(LocalDateTime.now());

        // calculate gross value and discount amount
        BigDecimal grossValue = cart.getCart().entrySet().stream()
                .map(entry -> {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();
                    BigDecimal productPrice = product.getPrice();

                    if (product.getDiscount() != null) {
                        BigDecimal discountPercent = product.getDiscount().getPercent();
                        BigDecimal discountAmount = productPrice.multiply(discountPercent.divide(BigDecimal.valueOf(100)));
                        productPrice = productPrice.subtract(discountAmount);
                        return productPrice.multiply(BigDecimal.valueOf(quantity));
                    }

                    return productPrice.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount = cart.getCart().entrySet().stream()
                .filter(entry -> entry.getKey().getDiscount() != null)
                .map(entry -> {
                    BigDecimal productValue = entry.getKey().getPrice()
                            .multiply(BigDecimal.valueOf(entry.getValue()));
                    BigDecimal discountPercent = entry.getKey().getDiscount().getPercent();
                    return productValue.multiply(discountPercent.divide(BigDecimal.valueOf(100)));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netValue = grossValue.multiply(BigDecimal.valueOf(0.8));

        BigDecimal personalDiscount = order.getUser().getPersonalDiscount();
        if (personalDiscount != null) {
            BigDecimal personalDiscountAmount = grossValue.multiply(personalDiscount.divide(BigDecimal.valueOf(100)));
            grossValue = grossValue.subtract(personalDiscountAmount);
        }

        order.setGrossValue(grossValue);
        order.setDiscountAmount(discountAmount);
        order.setNetValue(netValue);
        order.setPaid(false);

        clearCart(cart);
        orderRepository.save(order);
    }

    public void clearCart(CartDTO cart) {

        cart.getCart().forEach((p, quantity) -> {
            p.setQuantity(p.getQuantity() + quantity);
            productRepository.save(p);
        });
        cart.getCart().clear();
    }

    private OrderStatus getOrderStatus(String type) {
        OrderStatus acceptedOrderStatus = orderStatusRepository.findByType(type);
        if (acceptedOrderStatus == null) {
            acceptedOrderStatus = new OrderStatus();
            acceptedOrderStatus.setType(type);
            orderStatusRepository.save(acceptedOrderStatus);
        }
        return acceptedOrderStatus;
    }

    public void removeFromCart(int productId, CartDTO cart) {

        Product product = productRepository.getProductsById(productId).orElseThrow(()
                -> new NotFoundException("Product not found"));

        if (cart.getCart().containsKey(product)) {
            if (cart.getCart().get(product) > 1) {
                cart.getCart().put(product, cart.getCart().get(product) - 1);
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
        if (logger.id() != orderRepository.findOrderByUserId(logger.id()).getId()) {
            throw new BadRequestException("You can see only your own order status.");
        }
        Order o = opt.get();
        return o.getOrderStatus();
    }

    public ViewCartDTO viewCart(Map<Product, Integer> cart) {
        ViewCartDTO vcDTO = new ViewCartDTO();

        List<ViewProductCartDTO> viewProductCartDTOList = cart.entrySet().stream()
                .map(entry -> {
                    Product p = entry.getKey();
                    Integer quantity = entry.getValue();
                    ViewProductCartDTO viewProductCartDTO = new ViewProductCartDTO();
                    viewProductCartDTO.setName(p.getName());
                    viewProductCartDTO.setPrice(p.getPrice());
                    viewProductCartDTO.setQuantity(quantity);
                    return viewProductCartDTO;
                })
                .collect(Collectors.toList());

        vcDTO.setCheckCart(viewProductCartDTOList);

        BigDecimal totalGrossValue = cart.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        vcDTO.setGrossValue(totalGrossValue);
        return vcDTO;
    }

    private Order getOrderById(int orderId,int loggerId) {
        System.out.println(orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new NotFoundException("Order not found.");
        }
        if( optionalOrder.get().isPaid()){
            throw new BadRequestException("Order is already paid");
        }
        if (loggerId != optionalOrder.get().getUser().getId()){
            throw new BadRequestException("You can pay only your own orders.");
        }
        return optionalOrder.get();
    }

    public void payOrder(PaymentRequest pm, Logger logger) {

        Order order = getOrderById(pm.getOrderId(),logger.id());

        Payment payment = new Payment();
        payment.setUser(userRepository.getUserById(logger.id()));

        payment.setOrder(order);
        payment.setAmount(order.getGrossValue());

        payment.setCreatedAt(LocalDateTime.now());

        // try to pay

        payment.setProcessedAt(LocalDateTime.now());
        payment.setTransactionId(transactionIdGenerator());
        payment.setStatus(getOrderStatus("PAID").getType());


        order.setOrderStatus(orderStatusRepository.findByType("PAID"));
        order.setPaid(true);

        orderRepository.save(order);
        paymentRepository.save(payment);
    }

    public String transactionIdGenerator() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public Page<Order> getOrdersBy(int id, Pageable pageable) {
            return orderRepository.findAllByUserId(id,pageable);

    }
}
