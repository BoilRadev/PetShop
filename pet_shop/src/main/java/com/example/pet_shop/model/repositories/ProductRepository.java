package com.example.pet_shop.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.pet_shop.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> getProductsById(int id);
    Optional<Product> getProductByName(String name);
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllBySubcategoryId(int id,Pageable pageble);
}
