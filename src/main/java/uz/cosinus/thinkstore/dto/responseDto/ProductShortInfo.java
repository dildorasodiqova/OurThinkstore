package uz.cosinus.thinkstore.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductShortInfo {
    private UUID productId;
    private String productName;
    // shuyerda rasm ham kkmasmi
}
