package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.SubcategoryDTO;
import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.model.exceptions.BadRequestException;
import com.example.pet_shop.model.exceptions.UnauthorizedException;
import com.example.pet_shop.model.repositories.SubcategoryRepository;
import com.example.pet_shop.service.SubcategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class SubcategoryController extends AbstractController {
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    protected Logger logger;


    @PostMapping("/subcategories")
    public ResponseEntity<Subcategory> addSubcategory(@Valid @RequestBody SubcategoryDTO subcategoryDto) {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        Subcategory subcategory = subcategoryService.createSubcategory(subcategoryDto);
        return new ResponseEntity<>(subcategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable int subcategoryId) {
        if (!logger.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!logger.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
        subcategoryService.deleteSubcategory(subcategoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}