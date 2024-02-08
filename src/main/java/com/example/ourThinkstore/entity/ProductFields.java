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
@Entity(name = "productField")
public class ProductFields extends BaseEntity {
    private String name;

    @JoinColumn(name = "product_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductEntity product;

}

