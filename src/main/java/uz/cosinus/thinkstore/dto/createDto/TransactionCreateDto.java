package uz.cosinus.thinkstore.dto.createDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cosinus.thinkstore.enums.PaymentType;
import uz.cosinus.thinkstore.enums.TransactionStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionCreateDto {
    private UUID orderId;

    private Double price;

    private String paymentType;


}
