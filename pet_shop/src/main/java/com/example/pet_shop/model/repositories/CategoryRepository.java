package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
