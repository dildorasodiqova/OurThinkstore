package com.example.ourThinkstore.dto.responseDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProductResponseDto {
    private UUID id;
    private UUID orderId;
    private String productName;
    private int count;
    private Double price;
    private LocalDateTime createdDate;

    public OrderProductResponseDto(UUID id, String name, int count, Double price) {
       this.orderId = id;
       this.productName = name;
       this.count = count;
       this.price = price;
    }
}
