package com.example.pet_shop.service;

import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.model.DTOS.SubcategoryDTO;
import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.Subcategory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SubcategoryService extends AbstractService{

    public Subcategory createSubcategory(SubcategoryDTO subcategoryDto,int cId) {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryDto.getName());

        Optional<Category> optionalCategory = categoryRepository.findById(cId);
        if (optionalCategory.isEmpty()) {
            throw new EntityNotFoundException("Category with ID " + cId + " not found");
        }

        optionalCategory.get().getSubcategories().add(subcategory);
        subcategory.setCategory(optionalCategory.get());
        return subcategoryRepository.save(subcategory);
    }

    public void deleteSubcategory(int subcategoryId) {
        if(!subcategoryRepository.existsById(subcategoryId)){
            throw new BadRequestException("No such subcategory.");
        }
        subcategoryRepository.deleteById(subcategoryId);
    }


    public Page<Product> getProductsBySubcategoryId(int subcategoryId, Pageable pageable) {
        return subcategoryRepository.findAllProductsBySubcategoryId(subcategoryId, pageable);
    }
}