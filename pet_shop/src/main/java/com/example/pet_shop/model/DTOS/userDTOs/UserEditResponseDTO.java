package com.example.pet_shop.model.DTOS.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditResponseDTO {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String town;
    private String address;
    private boolean is_subscribed;

}
