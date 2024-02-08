package uz.cosinus.thinkstore.dto.createDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductLikeCreateDto {
    private UUID user;
    private UUID product;
}