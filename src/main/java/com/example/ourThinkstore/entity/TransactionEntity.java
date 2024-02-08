package com.example.ourThinkstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cosinus.thinkstore.enums.PaymentType;
import uz.cosinus.thinkstore.enums.TransactionStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "transaction")
public class TransactionEntity extends BaseEntity{
    @JoinColumn(name = "order_id")
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
