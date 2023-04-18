package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.SubcategoryDTO;
import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.model.repositories.SubcategoryRepository;
import com.example.pet_shop.service.SubcategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubcategoryController extends AbstractController {
    private SubcategoryRepository subcategoryRepository;
    private final SubcategoryService subcategoryService;
    private final ModelMapper mapper;

    @Autowired
    public SubcategoryController(SubcategoryRepository subcategoryRepository,SubcategoryService subcategoryService, ModelMapper mapper) {
       this.subcategoryRepository = subcategoryRepository;
        this.subcategoryService = subcategoryService;
        this.mapper = mapper;
    }

    @PostMapping("/subcategories/add")
    public Subcategory createSubcategory(@RequestBody SubcategoryDTO request) {
        Category category = subcategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")).getCategory_id();
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryRepository.findById(request.getId()).get().getName());
        subcategory.setCategory_id(category);
        return subcategoryRepository.save(subcategory);
    }

    @GetMapping("subcategories/{id}")
    public ResponseEntity<SubcategoryDTO> getSubcategory(@PathVariable int id) {
        Subcategory subcategory = subcategoryService.getSubcategoryById(id);
        return ResponseEntity.ok(mapper.map(subcategory, SubcategoryDTO.class));
    }
}