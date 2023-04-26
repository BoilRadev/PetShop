package com.example.pet_shop.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonIgnoreProperties
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    @JsonIgnoreProperties
    private Subcategory subcategory;

    @Column
    private int quantity;

    @Column
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties("images")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @JsonBackReference("discount-products")
    @JsonIgnoreProperties("discounts")
    private Discount discount;

    @ManyToMany(mappedBy = "products")
    @JsonBackReference
    @JsonIgnoreProperties
    private Set<Order> orders;

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