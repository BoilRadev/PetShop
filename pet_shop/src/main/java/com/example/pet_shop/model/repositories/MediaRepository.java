package com.example.pet_shop.model.repositories;

import com.example.pet_shop.model.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Image, Integer> {


}
