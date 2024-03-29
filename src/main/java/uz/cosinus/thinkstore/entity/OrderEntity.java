package uz.cosinus.thinkstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cosinus.thinkstore.enums.OrderStatus;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
@Table(name = "orders")
public class OrderEntity  extends BaseEntity{
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserEntity user;

    @Column(nullable = false)
    private double price;                                     ///total price

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderProductEntity> orderProducts;


    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHECK (status IN ('CREATED', 'IN_PROGRESS','DELIVERED', 'COMPLETED','APPROVED', 'CANCELED', 'REJECT'))")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;


    private boolean delivery;                                       /// true bo'lsa uyiga yetkazgan bo'ladi, false bo'lsa punkitga

    public OrderEntity(UserEntity user, double price, OrderStatus status, boolean b) {
        this.user = user;
        this.price = price;
        this.status = status;
        this.delivery = b;
    }
}
