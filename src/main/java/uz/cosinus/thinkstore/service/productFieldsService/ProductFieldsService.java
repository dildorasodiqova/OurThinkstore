package uz.cosinus.thinkstore.service.productFieldsService;
import uz.cosinus.thinkstore.dto.createDto.ProductFieldsCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductFieldsResponseDto;
import uz.cosinus.thinkstore.entity.ProductFields;

import java.util.List;
import java.util.UUID;

public interface ProductFieldsService {
    ProductFieldsResponseDto create (ProductFieldsCreateDto dto);
    ProductFields findById(UUID productFieldId);
    ProductFieldsResponseDto getById(UUID productFieldId);
    ProductFieldsResponseDto updateActive(UUID productFieldId, Boolean trueOrFalse);
    List<ProductFieldsResponseDto> getALl(int page, int size);
}
