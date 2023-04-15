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




