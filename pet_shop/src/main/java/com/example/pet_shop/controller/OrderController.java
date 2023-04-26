package com.example.pet_shop.controller;
import com.example.pet_shop.model.DTOS.OrderPayDTO;
import com.example.pet_shop.model.DTOS.orderDTO.CartDTO;
import com.example.pet_shop.model.DTOS.orderDTO.OrderInfoDTO;
import com.example.pet_shop.model.DTOS.orderDTO.PaymentRequest;
import com.example.pet_shop.model.DTOS.orderDTO.ViewCartDTO;
import com.example.pet_shop.model.entities.Order;
import com.example.pet_shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractController {
    @Autowired
    protected LoginManager loginManager;
    @Autowired
    private OrderService orderService;




    @PostMapping
    public OrderInfoDTO creatOrder(@Valid @RequestBody OrderPayDTO dto, HttpSession session) {
            CartDTO cart = getCart(session);
            return orderService.createOrder(loginManager.id(), cart, dto);
    }


    @GetMapping
    public ViewCartDTO viewCart(HttpSession session  ){
            CartDTO cart = getCart(session);
            return orderService.viewCart(cart.getCart());
    }

    @GetMapping("/{id}")
    public OrderInfoDTO getStatus(@PathVariable int id){
        checkLogin(loginManager);
        return orderService.getStatus(id, loginManager.id());
    }



    @GetMapping("/all")
    public ResponseEntity<Page<Order>> viewOrders(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        Page<Order> orders = orderService.getOrdersBy(loginManager.id(), pageable);
        return ResponseEntity.ok(orders);
    }




}
