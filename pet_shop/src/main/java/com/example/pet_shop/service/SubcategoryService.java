package com.example.pet_shop.service;

import com.example.pet_shop.controller.Logger;
import com.example.pet_shop.model.DTOS.SubcategoryDTO;
import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Order;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.Subcategory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SubcategoryService extends AbstractService{

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

    public Page<Product> getProductsBySubcategoryId(int subcategoryId, Pageable pageable) {
        return productRepository.findAllBySubcategoryId(subcategoryId,pageable);
    }
}