package com.example.ourThinkstore.dto.createDto;

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
public class OrderCreateDto {
    @NotNull(message = "User cannot be empty !")
    private UUID userId;

    private List<OrderProductCreateDto> products;
}
