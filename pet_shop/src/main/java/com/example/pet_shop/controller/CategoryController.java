package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.CategoryDTO;
import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ObjectMapper mapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ObjectMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        Category category = categoryService.addCategory(mapper.convertValue(categoryDTO, Category.class));
        return ResponseEntity.ok(mapper.convertValue(category, CategoryDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(mapper.convertValue(category, CategoryDTO.class));
    }

    // other endpoints...

}

