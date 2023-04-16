package com.example.pet_shop.model.DTOS.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {

    private String email;
    private String password;
}
