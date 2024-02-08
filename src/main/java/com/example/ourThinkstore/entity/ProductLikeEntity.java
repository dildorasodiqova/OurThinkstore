package com.example.ourThinkstore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "productLike")
public class ProductLikeEntity extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductEntity product;
}
