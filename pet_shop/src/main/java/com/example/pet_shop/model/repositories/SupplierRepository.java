package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Product;
import com.example.pet_shop.model.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
