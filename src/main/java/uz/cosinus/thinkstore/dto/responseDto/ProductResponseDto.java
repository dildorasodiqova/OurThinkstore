package uz.cosinus.thinkstore.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponseDto {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer count;
    private CategoryResponseDto category;
    private List<UUID> photos;
    private String brand;
    private LocalDateTime createdDate;
    private List<ProductFieldsResponseDto> productFields;
}
