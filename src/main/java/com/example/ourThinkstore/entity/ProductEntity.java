package com.example.ourThinkstore.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name= "product")
public class ProductEntity extends BaseEntity{

    @Column(unique = true)
    private String name;
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer count;

    @JoinColumn(name = "category_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CategoryEntity category;

    private String brand;



}
