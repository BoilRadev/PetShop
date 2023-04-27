package com.example.pet_shop.controller;

import com.example.pet_shop.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class PaymentController extends AbstractController{

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{order-id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable("order-id") int oderId, HttpSession s){
        getLoggedId(s);
        paymentService.payOrder(oderId, getLoggedId(s));
        return ResponseEntity.ok().body("Order successfully paid.");
    }
}
