package com.example.pet_shop.model.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryDTO {

        @NotBlank
        @Size(min = 2, max = 255)
        private String name;

        @NotNull
        private Integer categoryId;
}