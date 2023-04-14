package com.example.pet_shop.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String phone_number;

    @Column
    private BigDecimal personal_discount;

    @Column
    private String town;

    @Column
    private String address;

    @Column
    private LocalDateTime created_at; //TODO кога да сложим дата

    @Column
    private LocalDateTime deleted_at;

    @Column
    private boolean is_subscribed;

    @Column
    private boolean is_admin;

    @OneToMany(mappedBy = "owner")
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
