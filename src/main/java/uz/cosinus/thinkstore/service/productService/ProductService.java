package uz.cosinus.thinkstore.service.productService;

import org.apache.coyote.BadRequestException;
import uz.cosinus.thinkstore.dto.createDto.ProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductResponseDto;
import uz.cosinus.thinkstore.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductEntity findById(UUID productId);
    ProductResponseDto create (ProductCreateDto dto);
    ProductResponseDto getById(UUID productId);

    ProductResponseDto updateActive(UUID productId, Boolean trueOrFalse);
    ProductResponseDto update(UUID productId, ProductCreateDto dto);
    List<ProductResponseDto> getALlByCategory(int page, int size, UUID categoryId);
    List<ProductResponseDto> searchByNameOrCategoryName(String word, int page, int size);


}
