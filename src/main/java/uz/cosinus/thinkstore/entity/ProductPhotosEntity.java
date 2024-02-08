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
@Entity(name = "product_photos")
public class ProductPhotosEntity extends BaseEntity{
    @JoinColumn(name = "product_id")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private ProductEntity product;

    @JoinColumn(name = "photo_id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private AttachmentEntity photo;

    private int orderIndex;


}
