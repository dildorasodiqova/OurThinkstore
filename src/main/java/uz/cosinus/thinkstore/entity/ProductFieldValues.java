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
@Entity(name = "productFieldValue")
@Table(name = "product_field_value")
public class ProductFieldValues  extends BaseEntity{
    private String value;

    @JoinColumn(name = "product_field_id")
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private ProductFields productFields;

    @JoinColumn(name = "product_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductEntity product;

}
