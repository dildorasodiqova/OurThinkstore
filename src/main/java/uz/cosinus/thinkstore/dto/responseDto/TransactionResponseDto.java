package uz.cosinus.thinkstore.dto.responseDto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cosinus.thinkstore.entity.OrderEntity;
import uz.cosinus.thinkstore.enums.PaymentType;
import uz.cosinus.thinkstore.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponseDto {
    private UUID id;
    private UUID  orderId;

    private Double price;
    private PaymentType paymentType;
    private TransactionStatus status;

    private LocalDateTime canceledDate;
    private LocalDateTime payedDate;
    private LocalDateTime createdDate;


}
