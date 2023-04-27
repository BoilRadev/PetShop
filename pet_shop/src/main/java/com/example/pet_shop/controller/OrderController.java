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

<<<<<<< Updated upstream


=======
    @PutMapping("/orders/{productId}")
    public void addToCart(@PathVariable int productId, HttpSession s) {

        getLoggedId(s);
        CartDTO cart = new CartDTO();
        if (s.getAttribute("cart") != null) {
            cart = (CartDTO) s.getAttribute("cart");
        }
        orderService.addToCart(productId, cart);
        s.setAttribute("cart", cart);
    }

    @PostMapping("/orders/create")
    public ResponseEntity<?> creatOrder(@Valid @RequestBody OrderPayDTO dto, HttpSession s) {

        if (s.getAttribute("cart") != null) {
            CartDTO cart = (CartDTO) s.getAttribute("cart");
            orderService.createOrder(loginManager, cart, dto);
>>>>>>> Stashed changes

    @PostMapping
    public OrderInfoDTO creatOrder(@Valid @RequestBody OrderPayDTO dto, HttpSession session) {
            CartDTO cart = getCart(session);
            return orderService.createOrder(loginManager.id(), cart, dto);
    }
<<<<<<< Updated upstream


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


=======
    @DeleteMapping("/orders/{productId}")
    public void removeFromCart(@PathVariable int productId,HttpSession s){

        getLoggedId(s);
        CartDTO cart = new CartDTO();
        if (s.getAttribute("cart") != null) {
            cart = (CartDTO) s.getAttribute("cart");
        }
        orderService.removeFromCart(productId,cart);
        s.setAttribute("cart",cart);
    }

    @GetMapping("/orders/view")
    public ViewCartDTO viewCart(HttpSession s){

        if (s.getAttribute("cart") != null) {
            CartDTO cart = (CartDTO) s.getAttribute("cart");
            return orderService.viewCart(cart.getCart());
        }
        else {
            throw new BadRequestException("Nothing in cart");
        }
    }

    @PutMapping("/orders/{id}/status")
    public void editStatus(@PathVariable int id, HttpSession s){
        getLoggedId(s);
        orderService.editStatus(id);
    }

    @GetMapping("/orders/{id}/status")
    public OrderStatus getStatus(@PathVariable int id, HttpSession s){
        getLoggedId(s);
        return orderService.getStatus(id, loginManager);
    }

    @PostMapping("/orders/payments")
    public ResponseEntity<?> payOrder(@RequestBody PaymentRequest paymentRequest, HttpSession s){

        getLoggedId(s);
        orderService.payOrder(paymentRequest, loginManager);
        return ResponseEntity.ok().body("Order successfully paid.");
    }
>>>>>>> Stashed changes

    @GetMapping("/all")
    public ResponseEntity<Page<Order>> viewOrders(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        Page<Order> orders = orderService.getOrdersBy(loginManager.id(), pageable);
        return ResponseEntity.ok(orders);
    }
<<<<<<< Updated upstream




=======
>>>>>>> Stashed changes
}
