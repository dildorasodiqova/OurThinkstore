package uz.cosinus.thinkstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "category")
@Table(name = "category")
public class CategoryEntity extends BaseEntity{
    @Column(unique = true)
    private String name;

    @JoinColumn(name = "photo_id")
    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private AttachmentEntity photo;

    private UUID parentId;

    private String description;

}
