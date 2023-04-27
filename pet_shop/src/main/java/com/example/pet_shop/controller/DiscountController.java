package com.example.pet_shop.controller;


import com.example.pet_shop.model.DTOS.discountDTO.DiscountAddDTO;
import com.example.pet_shop.model.DTOS.discountDTO.DiscountEditDTO;
import com.example.pet_shop.model.DTOS.discountDTO.DiscountInfoDTO;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.UnauthorizedException;
import com.example.pet_shop.service.DiscountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiscountController extends AbstractController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/products/{productId}/discounts")
<<<<<<< Updated upstream
    public DiscountInfoDTO addDiscountToProduct(@PathVariable int productId, @RequestBody DiscountAddDTO dto) {
        checkAuthorization(loginManager);
        return discountService.addDiscount(dto, productId);
    }

    @PutMapping("/discounts/{id}")
    public DiscountInfoDTO editDiscount(@PathVariable int id, DiscountEditDTO dto) {
        checkAuthorization(loginManager);
=======
    public DiscountInfoDTO addDiscountToProduct(@PathVariable int productId, @RequestBody DiscountAddDTO dto, HttpSession s){

        isAdminLoggedIn(s);
        return discountService.addDiscount(dto,productId);
    }

    @PutMapping("/discounts/{id}")
    public DiscountInfoDTO editDiscount(@PathVariable int id, DiscountEditDTO dto, HttpSession s){

        isAdminLoggedIn(s);
>>>>>>> Stashed changes
        return discountService.editDiscount(id, dto);
    }

    @DeleteMapping("/discounts/{id}")
<<<<<<< Updated upstream
    public ResponseEntity<String> deleteDiscount(@PathVariable int id) {
        checkAuthorization(loginManager);
        discountService.delete(id);
        return ResponseEntity.ok("Discount deleted successfully.");
    }

=======
    public ResponseEntity<String> deleteDiscount(@PathVariable int id, HttpSession s){

        isAdminLoggedIn(s);
        discountService.delete(id);
        return ResponseEntity.ok("Discount deleted successfully.");
    }
>>>>>>> Stashed changes
}
