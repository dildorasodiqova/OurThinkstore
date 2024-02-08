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
@Entity(name = "productField")
@Table(name = "product_field")
public class ProductFields extends BaseEntity {
    private String name;

    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductEntity product;

    private Boolean isRequired; //  bu shu fieldni toldirish kkligini bildiradi.

}

