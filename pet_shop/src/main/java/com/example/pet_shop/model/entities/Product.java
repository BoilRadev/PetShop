package com.example.pet_shop.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonIgnore
    @JsonBackReference
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;


    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private Double price;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties("images")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @JsonIgnoreProperties("discounts")
    private Discount discount;


    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}