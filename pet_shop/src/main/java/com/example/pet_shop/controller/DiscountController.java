package com.example.pet_shop.controller;


import com.example.pet_shop.model.DTOS.discountDTO.DiscountAddDTO;
import com.example.pet_shop.model.DTOS.discountDTO.DiscountEditDTO;
import com.example.pet_shop.model.DTOS.discountDTO.DiscountInfoDTO;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.UnauthorizedException;
import com.example.pet_shop.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiscountController extends AbstractController {

    @Autowired
    private DiscountService discountService;
    @Autowired
    protected LoginManager loginManager;

    @PostMapping("/products/{productId}/discounts")
    public DiscountInfoDTO addDiscountToProduct(@PathVariable int productId, @RequestBody DiscountAddDTO dto) {
        checkAuthorization(loginManager);
        return discountService.addDiscount(dto, productId);
    }

    @PutMapping("/discounts/{id}")
    public DiscountInfoDTO editDiscount(@PathVariable int id, DiscountEditDTO dto) {
        checkAuthorization(loginManager);
        return discountService.editDiscount(id, dto);
    }

    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<String> deleteDiscount(@PathVariable int id) {
        checkAuthorization(loginManager);
        discountService.delete(id);
        return ResponseEntity.ok("Discount deleted successfully.");
    }

}
