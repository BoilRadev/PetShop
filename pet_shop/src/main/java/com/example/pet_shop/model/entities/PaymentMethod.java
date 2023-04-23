package com.example.pet_shop.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "payment_methods")
@NoArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String type;

   @OneToMany(mappedBy = "paymentMethod")
   @JsonIgnore
    private List<Order> orders;
}