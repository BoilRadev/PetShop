package com.example.pet_shop.service;

import com.example.pet_shop.model.DTOS.SubcategoryDTO;
import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.model.repositories.CategoryRepository;
import com.example.pet_shop.model.repositories.SubcategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public Subcategory createSubcategory(SubcategoryDTO subcategoryDto) {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryDto.getName());

        Optional<Category> optionalCategory = categoryRepository.findById(subcategoryDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new EntityNotFoundException("Category with ID " + subcategoryDto.getCategoryId() + " not found");
        }

        subcategory.setCategory(optionalCategory.get());

        return subcategoryRepository.save(subcategory);
    }

    public void deleteSubcategory(Integer subcategoryId) {
        subcategoryRepository.deleteById(subcategoryId);
    }

    public Optional<Subcategory> getSubcategoryById(Integer subcategoryId) {
        return subcategoryRepository.findById(subcategoryId);
    }
}