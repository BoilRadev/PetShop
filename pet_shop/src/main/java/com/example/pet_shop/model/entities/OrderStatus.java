package com.example.pet_shop.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "order_statuses")
@NoArgsConstructor
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String type;

    @OneToMany(mappedBy = "orderStatus")
    @JsonManagedReference
    private List<Order> order;

}
