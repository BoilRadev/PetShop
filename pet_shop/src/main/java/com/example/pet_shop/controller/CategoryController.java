package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.CategoryDTO;
import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody @Valid CategoryDTO categoryDto,HttpSession s) {
        isAdminLoggedIn(s);
        Category category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId, HttpSession s) {
        isAdminLoggedIn(s);
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<Subcategory>> getSubcategoriesByCategoryId(@PathVariable int categoryId) {
        List<Subcategory> subcategories = categoryService.getSubcategoriesByCategoryId(categoryId);
        return new ResponseEntity<>(subcategories, HttpStatus.OK);
    }
}

