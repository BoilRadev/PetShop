package com.example.pet_shop.controller;
import com.example.pet_shop.model.DTOS.OrderPayDTO;
import com.example.pet_shop.model.DTOS.orderDTO.CartDTO;
import com.example.pet_shop.model.DTOS.orderDTO.PaymentRequest;
import com.example.pet_shop.model.DTOS.orderDTO.ViewCartDTO;
import com.example.pet_shop.model.entities.OrderStatus;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController extends AbstractController {
    @Autowired
    protected Logger logger;
    @Autowired
    private OrderService orderService;

    @PutMapping("/orders/{productId}")
    public void addToCart(@PathVariable int productId, HttpSession session) {

        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        CartDTO cart = new CartDTO();
        if (session.getAttribute("cart") != null) {
            cart = (CartDTO) session.getAttribute("cart");
        }
        orderService.addToCart(productId, cart);
        session.setAttribute("cart", cart);
    }

    @PostMapping("/orders/create")
    public ResponseEntity<?> creatOrder(@Valid @RequestBody OrderPayDTO dto, HttpSession session) {

        if (session.getAttribute("cart") != null) {
            CartDTO cart = (CartDTO) session.getAttribute("cart");
            orderService.createOrder(logger, cart, dto);

        } else {
            throw new BadRequestException("Nothing in cart");
        }
        return ResponseEntity.ok().body("Order created successfully.");
    }
    @DeleteMapping("/orders/{productId}")
    public void removeFromCart(@PathVariable int productId,HttpSession session){

        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        CartDTO cart = new CartDTO();
        if (session.getAttribute("cart") != null) {
            cart = (CartDTO) session.getAttribute("cart");
        }
        orderService.removeFromCart(productId,cart);
        session.setAttribute("cart",cart);
    }

    @GetMapping("/orders/view")
    public ViewCartDTO viewCart(HttpSession session  ){

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
        return orderService.getStatus(id,logger);
    }

    @PostMapping("/orders/payments")
    public ResponseEntity<?> payOrder(@RequestBody PaymentRequest paymentRequest){

        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        orderService.payOrder(paymentRequest,logger);
        return ResponseEntity.ok().body("Order successfully paid.");
    }


}
