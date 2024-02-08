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
@Entity(name = "order")
public class OrderEntity  extends BaseEntity{
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @Column(nullable = false)
    private double price; ///total price

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProductEntity> orderProducts;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    private boolean delivery; /// true bo'lsa uyiga yetkazgan bo'ladi, false bo'lsa punkitga

    public OrderEntity(UserEntity user, double price, OrderStatus status, boolean b) {
        this.user = user;
        this.price = price;
        this.status = status;
        this.delivery = b;
    }
}
