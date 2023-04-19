package com.example.pet_shop.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    @JsonIgnoreProperties("products")
    private Subcategory subcategory;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("subcategories")
    private Category category;
    @Column
    private int quantity;

    @Column
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
}