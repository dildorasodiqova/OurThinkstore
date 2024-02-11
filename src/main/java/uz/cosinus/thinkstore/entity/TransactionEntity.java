package uz.cosinus.thinkstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cosinus.thinkstore.enums.PaymentType;
import uz.cosinus.thinkstore.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "transaction")
@Table(name = "transactions")
public class TransactionEntity extends BaseEntity{
    @Column(name = "order_id")
    private UUID orderId;
    @JoinColumn(name = "order_id",insertable = false,updatable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private OrderEntity order;

    private Double price;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime canceledDate;
    private LocalDateTime payedDate;


}
