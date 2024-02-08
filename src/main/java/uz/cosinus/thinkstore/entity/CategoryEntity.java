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
@Entity(name = "category")
public class CategoryEntity extends BaseEntity{
    @Column(unique = true)
    private String name;

    @JoinColumn(name = "photo_id")
    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private AttachmentEntity photo;

    @JoinColumn(name = "parent_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private CategoryEntity parent;

    private String description;

}
