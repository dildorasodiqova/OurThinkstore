package com.example.ourThinkstore.dto.responseDto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BasketProductResponseDto {
    private UUID id;
    private UUID productId;
    private String name;
    private Double price;
    private Integer count;

}
