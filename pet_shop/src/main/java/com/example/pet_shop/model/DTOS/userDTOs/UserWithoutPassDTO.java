package com.example.pet_shop.model.DTOS.userDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutPassDTO {

    private int id;
    private String email;
}
