package uz.cosinus.thinkstore.dto.createDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeedBackCreateDto {
    @NotNull(message = "Product cannot be empty !")
    private UUID productId;

    private int rate;

    private String text;
}
