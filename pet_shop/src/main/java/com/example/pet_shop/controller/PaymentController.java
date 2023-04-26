package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.orderDTO.PaymentRequest;
import com.example.pet_shop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class PaymentController extends AbstractController{
    @Autowired
    private LoginManager loginManager;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{order-id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable("order-id") int oderId){
        checkLogin(loginManager);
        paymentService.payOrder(oderId, loginManager.id());
        return ResponseEntity.ok().body("Order successfully paid.");
    }

}
