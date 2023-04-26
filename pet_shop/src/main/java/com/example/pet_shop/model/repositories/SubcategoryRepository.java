package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Category;
import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    List<Subcategory> findAllByCategory_Id(int categoryId);

    @Query("SELECT p FROM Product p WHERE p.subcategory.id = :subcategoryId")
    Page<Product> findAllProductsBySubcategoryId(@Param("subcategoryId") int subcategoryId, Pageable pageable);
}
