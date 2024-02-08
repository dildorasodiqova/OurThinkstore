package com.example.ourThinkstore.service.bucketService;

import uz.cosinus.thinkstore.dto.createDto.BasketProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.BasketProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface BasketProductService {
    BasketProductResponseDto getById(UUID basketId);
    List<BasketProductResponseDto> getAll(int page, int size);
    BasketProductResponseDto create(BasketProductCreateDto dto);
    List<BasketProductResponseDto> getUserProduct(UUID userId);

    BasketProductResponseDto updateProductCount(UUID productId, UUID userId, int count);


}
