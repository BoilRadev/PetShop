package com.example.pet_shop.service;

import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.model.entities.Order;
import com.example.pet_shop.model.entities.OrderStatus;
import com.example.pet_shop.model.entities.Payment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PaymentService extends AbstractService{

    @Transactional
    public void payOrder(int orderId, int userId) {

        Order order = getOrderById(orderId,userId);

        Payment payment = new Payment();
        payment.setUser(userRepository.getUserById(userId));

        payment.setOrder(order);
        payment.setAmount(order.getGrossValue());
        payment.setCreatedAt(LocalDateTime.now());


        if (order.getPaymentMethod().getType().equals("Cash")) {
            throw new BadRequestException("Payment can only be done in the office by an admin");
        } else {
            boolean isPaid = Math.random() >= 0.4;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isPaid) {

                payment.setProcessedAt(LocalDateTime.now());
                payment.setTransactionId(transactionIdGenerator());
                payment.setStatus(getOrderStatus("PAID").getType());

                order.setOrderStatus(orderStatusRepository.findByType("PAID"));
                order.setPaid(true);

                orderRepository.save(order);
                paymentRepository.save(payment);
            } else {
                throw new BadRequestException("Payment failed. Please try again later.");
            }
        }
    }
    public String transactionIdGenerator() {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
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
}
