package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.*;
import com.example.pet_shop.model.entities.OrderStatus;
import com.example.pet_shop.model.DTOS.OrderInfoDTO;
import com.example.pet_shop.model.exceptions.BadRequestException;

import com.example.pet_shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController extends AbstractController {
    @Autowired
    protected Logger logger;
    @Autowired
    private OrderService orderService;


    @PostMapping("/orders")
    public void addToCart(@RequestBody AddToCartDTO dto, HttpSession session) {

        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        CartDTO cart = new CartDTO();
        if (session.getAttribute("cart") != null) {
            cart = (CartDTO) session.getAttribute("cart");
        }
        orderService.addToCart(dto,cart);
        session.setAttribute("cart",cart);
    }


    @DeleteMapping("/orders")
    public CartDTO removeFromCart(@RequestBody AddToCartDTO dto,HttpSession session){

        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        CartDTO cart = new CartDTO();
        if (session.getAttribute("cart") != null) {
            cart = (CartDTO) session.getAttribute("cart");
        }
        return orderService.removeFromCart(dto,cart);

    }

    @GetMapping("/orders/view")
    public ViewCartDTO viewCart( HttpSession session  ){

        if (session.getAttribute("cart") != null) {
            CartDTO cart = (CartDTO) session.getAttribute("cart");
            return orderService.viewCart(cart.getCart());
        }
        else {
            throw new BadRequestException("Nothing in cart");
        }
    }

    @PutMapping("/orders/{id}/status")
    public void editStatus(@PathVariable int id){
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        orderService.editStatus(id);
    }

    @GetMapping("/orders/{id}/status")
    public OrderStatus getStatus(@PathVariable int id){
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        return orderService.getStatus(id);
    }

}
