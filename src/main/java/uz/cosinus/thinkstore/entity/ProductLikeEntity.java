package uz.cosinus.thinkstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "productLike")
@Table(name = "product_ike")
public class ProductLikeEntity extends BaseEntity {
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductEntity product;
}
