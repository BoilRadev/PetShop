package com.example.pet_shop.service;

import com.example.pet_shop.controller.LoginManager;
import com.example.pet_shop.model.DTOS.OrderPayDTO;
import com.example.pet_shop.model.DTOS.orderDTO.*;
import com.example.pet_shop.model.entities.*;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.OrderRepository;
import com.example.pet_shop.model.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;




    public OrderInfoDTO createOrder(int userId, CartDTO cart, @Valid OrderPayDTO dto) {
        Order order = new Order();
        order.setUser(userRepository.getUserById(userId));
        order.setPaymentMethod(paymentMethodRepository.findById(dto.getPaymentMethodId()).get());
        order.setOrderStatus(getOrderStatus("ACCEPTED"));
        order.setAddress(userRepository.getUserById(userId).getAddress());
        order.setCreatedAt(LocalDateTime.parse(LocalDateTime.now().toString()));

        cart.getCart().forEach((p, quantity) -> {
            if (p.getQuantity() < quantity) {
                throw new NotFoundException("Not enough products.");
            }
        });

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
            discountAmount = discountAmount.add(personalDiscountAmount);
        }

        order.setGrossValue(grossValue);
        order.setDiscountAmount(discountAmount);
        order.setNetValue(netValue);
        order.setPaid(false);

        orderRepository.save(order);
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setPaid(order.isPaid());
        orderInfoDTO.setStatusType(order.getOrderStatus().getType());
        orderInfoDTO.setDiscountAmount(order.getDiscountAmount());
        orderInfoDTO.setOrderId(order.getId());
        orderInfoDTO.setGrossValue(order.getGrossValue());

        clearCart(cart,order);

        return orderInfoDTO;
    }

    public void clearCart(CartDTO cart,Order order) {
        cart.getCart().forEach((p, quantity) -> {
            synchronized (p) {
                if (p.getQuantity() < quantity) {
                    throw new NotFoundException("Not enough products.");
                }
                p.setQuantity(p.getQuantity() - quantity);
                productRepository.save(p);
                order.getProducts().add(p);
            }
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


    public OrderInfoDTO getStatus(int orderId, int userId) {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isEmpty()) {
            throw new BadRequestException("No order found with id: " + orderId);
        }
        if (userId != orderRepository.findOrderByUserId(userId).getId()) {
            throw new BadRequestException("You can see only your own order status.");
        }
        Order o = opt.get();

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setStatusType(o.getOrderStatus().getType());
        orderInfoDTO.setDiscountAmount(o.getDiscountAmount());
        orderInfoDTO.setOrderId(o.getId());
        orderInfoDTO.setPaid(o.isPaid());
        return orderInfoDTO;
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




    public Page<Order> getOrdersBy(int id, Pageable pageable) {
            return orderRepository.findAllByUserId(id,pageable);

    }
}
