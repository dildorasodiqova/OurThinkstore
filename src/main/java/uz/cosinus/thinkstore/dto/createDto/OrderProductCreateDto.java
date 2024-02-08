package uz.cosinus.thinkstore.dto.createDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProductCreateDto{
    @NotNull(message = "Product cannot be empty !")
    private UUID productId;

    @Min(value = 1, message = "Count should be at least 1")
    private int count;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0.0")
    private Double price;
}




