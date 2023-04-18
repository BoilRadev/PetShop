package com.example.pet_shop.model.DTOS;

import com.example.pet_shop.model.entities.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class SubcategoryDTO {
        private int id;
        int categoryId;
}