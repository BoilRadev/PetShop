package com.example.pet_shop.model.DTOS.userDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutPassDTO {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String town;
    private String address;
    private LocalDateTime createdAt;
    private boolean isSubscribed;
}
