package uz.cosinus.thinkstore.dto.createDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.cosinus.thinkstore.enums.PaymentType;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreateDto {
    private List<OrderProductsCreate> products;
    private PaymentType paymentType;
}
