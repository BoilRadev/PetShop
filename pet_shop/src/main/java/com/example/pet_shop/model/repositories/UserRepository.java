package com.example.pet_shop.model.repositories;



import com.example.pet_shop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    Optional<User> getByEmail(String email);
    Optional<User> getUserById(int id);

    List<User> findByIsSubscribedTrue();


}