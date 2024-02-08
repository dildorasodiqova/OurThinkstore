package com.example.ourThinkstore.service.productLikeService;

import uz.cosinus.thinkstore.dto.createDto.ProductLikeCreateDto;

import java.util.UUID;

public interface ProductLikeService {
    String create (ProductLikeCreateDto dto);
    Long getALlLike(UUID productId);
}
