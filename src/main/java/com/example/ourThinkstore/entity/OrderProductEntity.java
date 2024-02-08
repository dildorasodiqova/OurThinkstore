package com.example.ourThinkstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "order_product")
public class OrderProductEntity extends BaseEntity {
    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private OrderEntity order;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ProductEntity product;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Double price;
}

