package com.example.pet_shop.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "suppliers")
    private Supplier supplier_id;

    @ManyToOne
    @JoinColumn(name = "subcategories")
    private Subcategory subcategory_id;

    @Column
    private int quantity;

    @Column
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private Set<Image> images;

    @ManyToOne
    @JoinColumn(name = "discounts")
    private Discount discount_id;
}