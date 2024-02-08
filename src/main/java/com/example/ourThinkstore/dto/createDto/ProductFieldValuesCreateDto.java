package com.example.ourThinkstore.dto.createDto;

import jakarta.validation.constraints.NotBlank;
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
public class ProductFieldValuesCreateDto {
    @NotBlank(message = "Value cannot be empty !")
    private String value;

    @NotNull(message = "Product field id cannot be empty !")
    private UUID productFieldId;

    @NotNull(message = "Product id cannot be empty !")
    private UUID productId;

}
