package com.example.ourThinkstore.dto.createDto;
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
public class ProductFieldsCreateDto {
    @NotBlank(message = "Name cannot be empty !")
    private String name;

    @NotNull(message = "Product cannot be empty !")
    private UUID productId;

    private List<ProductFieldValuesCreateDto> productFieldValues;


}
