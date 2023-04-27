package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.SubcategoryDTO;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.service.SubcategoryService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping
    public ResponseEntity<Subcategory> createSubcategory(@PathVariable int categoryId,
                                                         @Valid @RequestBody SubcategoryDTO subcategoryDTO, HttpSession s) {
        isAdminLoggedIn(s);
        Subcategory subcategory = subcategoryService.createSubcategory(subcategoryDTO, categoryId);
        return new ResponseEntity<>(subcategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<?> deleteSubcategory(@PathVariable int subcategoryId, HttpSession s) {
        isAdminLoggedIn(s);
        subcategoryService.deleteSubcategory(subcategoryId);
        return ResponseEntity.ok().body("Subcategory deleted.");
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