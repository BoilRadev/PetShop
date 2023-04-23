package com.example.pet_shop.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
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
@Builder
public class Order implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    @JsonIgnore
    private PaymentMethod paymentMethod;

    @OneToOne(mappedBy = "order")
    @JsonIgnore
    private Payment payment;

    @Column
    private LocalDateTime createdAt;

    @Column
    private BigDecimal grossValue;

    @Column
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
    @JsonIgnoreProperties
    private Set<Product> products;

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }
}
