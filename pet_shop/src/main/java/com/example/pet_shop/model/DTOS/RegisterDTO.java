package com.example.pet_shop.model.DTOS;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String email;
    private String password;
<<<<<<< HEAD

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String townName;
    private String address;
    private LocalDateTime time;
    private boolean isSubscribed;
    private boolean isAdmin;
=======
>>>>>>> e514a4f1cc5903adf21ea50d9813d3a1c68abd7c
    private String confirmPassword;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "phone_number")
    private String phone_number;

    private String town;

    private String address;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "is_subscribed")
    private boolean is_subscribed;

    @Column(name = "is_admin")
    private boolean is_admin;
}




