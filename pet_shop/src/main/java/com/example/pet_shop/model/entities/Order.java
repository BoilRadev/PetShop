package com.example.pet_shop.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    @JsonBackReference
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    @JsonBackReference
    private PaymentMethod paymentMethod;
    @OneToOne(mappedBy = "order")
    private Payment payment;
    @Column
    private LocalDateTime createdAt;

    @Column
    private BigDecimal grossValue;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column
    private BigDecimal netValue;

    @Column
    private String address;

    @Column
    private boolean isPaid;
    @ManyToMany
    @JoinTable(
            name = "orders_have_products",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    @JsonManagedReference
    private Set<Product> products;

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }
}
