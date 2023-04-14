package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
}
