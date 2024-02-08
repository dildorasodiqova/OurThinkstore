package uz.cosinus.thinkstore.dto.createDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;
    private Double price;

    @Min(value = 1, message = "Count should be at least 1")
    private Integer count;

    @NotNull(message = "Category cannot be empty !")
    private UUID categoryId;

    private List<UUID> photos;

    @NotBlank(message = "Brand cannot be empty")
    private String brand;

    private List<ProductFieldsCreateDto> productFields;



}
