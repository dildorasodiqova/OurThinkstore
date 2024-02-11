package uz.cosinus.thinkstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "basketProduct")
@Table(name = "basket_product")
public class BasketProductEntity extends BaseEntity{

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserEntity user;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ProductEntity product;

    private int count;
}
