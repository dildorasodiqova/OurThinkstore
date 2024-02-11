package uz.cosinus.thinkstore.dto.createDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProductsCreate {
    @NotNull(message = "Product cannot be empty !")
    private UUID productId;

    @Min(value = 1, message = "Count should be at least 1")
    private int count;
}
