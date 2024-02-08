package com.example.ourThinkstore.dto.responseDto;

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
public class ProductFieldValuesResponseDto {
    private UUID id;
    private String value;
    private UUID productFields;
    private UUID product;
    private LocalDateTime createdDate;
}
