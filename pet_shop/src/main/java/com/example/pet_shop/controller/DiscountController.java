package com.example.pet_shop.controller;


import com.example.pet_shop.model.DTOS.discountDTO.DiscountAddDTO;
import com.example.pet_shop.model.DTOS.discountDTO.DiscountInfoDTO;

import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiscountController extends AbstractController {

    @Autowired
    private DiscountService discountService;
    @Autowired
    protected Logger logger;
    @PostMapping("/discounts")
    public DiscountInfoDTO addDiscount(@RequestBody DiscountAddDTO dto){
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        return discountService.addDiscount(dto);
    }

    @PutMapping("/discounts/{id}")
    public DiscountInfoDTO editDiscount(@PathVariable int id){
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        return discountService.editDiscount(id);
    }

    @DeleteMapping("/discounts/{id}")
    public void deleteDiscount(@PathVariable int id){
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }

    }
}
