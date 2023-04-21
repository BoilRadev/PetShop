package com.example.pet_shop.model.DTOS.productDTOs;

import com.example.pet_shop.model.entities.Subcategory;
import com.example.pet_shop.model.entities.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO implements Comparable{

    private int id;
    private String name;
    private String description;
    private Supplier supplier;
    private Subcategory subcategory;
    private int quantity;
    private BigDecimal price;

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfoDTO that = (ProductInfoDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
