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
@Entity(name = "feedback")
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity {
    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private int rate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text; 

}
