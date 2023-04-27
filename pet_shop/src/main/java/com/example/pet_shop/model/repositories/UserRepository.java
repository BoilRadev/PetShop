package com.example.pet_shop.model.repositories;



import com.example.pet_shop.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    Optional<User> getByEmail(String email);
    User getUserById(int id);
    Page<User> findAll(Pageable pageable);


    Optional<User>findAllByConfirmationToken(String token);

    @Query(value = "SELECT * FROM users   WHERE enable = false AND created_at <= created_at",nativeQuery = true)
    List<User> findAllByEnableFalseAAndDateTimeRegistration(@Param("cutoffTime") LocalDateTime cutoffTime);


}