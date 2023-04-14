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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "personal_discount")
    private BigDecimal personalDiscount;

    @Column
    private String town;

    @Column
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt; //TODO кога да сложим дата

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_subscribed")
    private boolean isSubscribed;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @OneToMany(mappedBy = "users")
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "users")
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
