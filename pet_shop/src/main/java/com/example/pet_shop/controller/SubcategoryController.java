package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.SubcategoryDTO;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.UnauthorizedException;
import com.example.pet_shop.service.SubcategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories/{categoryId}/subcategories")
public class SubcategoryController extends AbstractController {

    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    protected Logger logger;

    @PostMapping
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

    @GetMapping("/{subcategoryId}/products")
    public ResponseEntity<Page<Product>> getProductsBySubcategoryId(@PathVariable int subcategoryId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @RequestParam(defaultValue = "id") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Product> products = subcategoryService.getProductsBySubcategoryId(subcategoryId, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}