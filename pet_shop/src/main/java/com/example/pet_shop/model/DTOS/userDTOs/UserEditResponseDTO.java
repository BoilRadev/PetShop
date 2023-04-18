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
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String town;
    private String address;
    private boolean isSubscribed;

}
