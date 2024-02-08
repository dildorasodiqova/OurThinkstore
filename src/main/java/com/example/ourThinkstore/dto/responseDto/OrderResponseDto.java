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
public class OrderResponseDto {
    private UUID id;
    private UUID userId;
    private double price;
    private List<OrderProductResponseDto> orderProducts;
    private LocalDateTime createdDate;

    public OrderResponseDto(UUID id, double price, List<OrderProductResponseDto> save) {
        this.userId = id;
        this.price = price;
        this.orderProducts = save;
    }
}
