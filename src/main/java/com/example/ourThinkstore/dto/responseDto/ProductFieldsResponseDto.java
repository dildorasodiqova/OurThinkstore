package com.example.ourThinkstore.dto.responseDto;
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
public class ProductFieldsResponseDto {
    private UUID id;
    private List<ProductFieldValuesResponseDto> productFieldValues;
    private String name;
    private ProductResponseDto product;
    private LocalDateTime createdDate;


}
