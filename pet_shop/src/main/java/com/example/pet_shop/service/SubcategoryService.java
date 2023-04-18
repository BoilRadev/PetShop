package com.example.pet_shop.service;

import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.model.exceptions.NotFoundException;
import com.example.pet_shop.model.repositories.CategoryRepository;
import com.example.pet_shop.model.repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SubcategoryService extends AbstractService{
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public Subcategory addSubcategory(Subcategory subcategory) {
        Category category = subcategory.getCategory_id();
        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found. Please add a new category here: /category/add");
        }
        subcategory.setCategory_id(optionalCategory.get());
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory getSubcategoryById(int id) {
        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findById(id);
        if (optionalSubcategory.isEmpty()) {
            throw new NotFoundException("Subcategory not found");
        }
        return optionalSubcategory.get();
    }
}
