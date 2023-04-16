package com.example.pet_shop.model.DTOS.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserBasicInfoDTO {

    private int id;
    private String email;
    private int age;
    private String profileImageUrl;
}
