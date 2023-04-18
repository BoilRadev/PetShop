package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Subcategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
    <S extends Subcategory> S save(S subcategory);


    Category findByName(String name);
    @Modifying
    @Query("UPDATE Category c SET c.name = :newName WHERE c.id = :categoryId")
    void updateName(@Param("categoryId") int categoryId, @Param("newName") String newName);
    @Transactional
    default Subcategory create(String name, Category category) {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(name);
        subcategory.setCategory_id(category);
        return save(subcategory);
    }
}
