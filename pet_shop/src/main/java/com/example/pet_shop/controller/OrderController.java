package com.example.pet_shop.controller;
import com.example.pet_shop.model.DTOS.OrderPayDTO;
import com.example.pet_shop.model.DTOS.orderDTO.CartDTO;
import com.example.pet_shop.model.DTOS.orderDTO.OrderInfoDTO;
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
    private OrderService orderService;

    @PostMapping
    public OrderInfoDTO creatOrder(@Valid @RequestBody OrderPayDTO dto, HttpSession s) {
            CartDTO cart = getCart(s);
            return orderService.createOrder(getLoggedId(s), cart, dto);
    }

    @GetMapping
    public ViewCartDTO viewCart(HttpSession s){
            CartDTO cart = getCart(s);
            return orderService.viewCart(cart.getCart());
    }

    @GetMapping("/{id}")
    public OrderInfoDTO getStatus(@PathVariable int id, HttpSession s){
        getLoggedId(s);
        return orderService.getStatus(id,getLoggedId(s));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Order>> viewOrders(@PageableDefault(page = 0, size = 10, sort = "id")
                                                              Pageable pageable, HttpSession s) {
        Page<Order> orders = orderService.getOrdersBy(getLoggedId(s), pageable);
        return ResponseEntity.ok(orders);
    }
}
