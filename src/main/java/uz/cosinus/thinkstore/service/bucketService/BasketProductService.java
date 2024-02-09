package uz.cosinus.thinkstore.service.bucketService;

import uz.cosinus.thinkstore.dto.createDto.BasketProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.BasketProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface BasketProductService {

    List<BasketProductResponseDto> getAll(int page, int size);
    BasketProductResponseDto create(BasketProductCreateDto dto, UUID userId);
    List<BasketProductResponseDto> getUserProduct(UUID userId);

    BasketProductResponseDto updateProductCount(UUID productId, UUID userId, int count);


}
