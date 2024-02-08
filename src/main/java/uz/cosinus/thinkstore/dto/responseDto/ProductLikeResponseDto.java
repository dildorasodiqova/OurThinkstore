package uz.cosinus.thinkstore.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductLikeResponseDto {
    private UUID id;
    private UserShortInfo user;
    private ProductShortInfo product;
    private LocalDateTime createdDate;
}
