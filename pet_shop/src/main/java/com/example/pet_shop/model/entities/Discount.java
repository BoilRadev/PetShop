package com.example.pet_shop.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String description;

    @Column
    private BigDecimal percent;

    @Column
    private LocalDate fromDate;

    @Column
    private LocalDate toDate;

    @Column
    private boolean isActive;

    @OneToMany(mappedBy = "discount")
    private List<Product> products = new ArrayList<>();





}

