package uz.cosinus.thinkstore.service.productFieldValues;

import uz.cosinus.thinkstore.dto.createDto.ProductFieldValuesCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductFieldValuesResponseDto;
import uz.cosinus.thinkstore.entity.ProductFieldValues;

import java.util.List;
import java.util.UUID;

public interface ProductFieldValuesService {
    String create (List<ProductFieldValuesCreateDto> dto);
    ProductFieldValues findById(UUID productFieldId);
    ProductFieldValuesResponseDto getById(UUID productFieldId);
    ProductFieldValuesResponseDto updateActive(UUID productFieldId, Boolean trueOrFalse);
    List<ProductFieldValuesResponseDto> getALl(int page, int size);

    List<ProductFieldValuesResponseDto> findAllByFieldId(UUID fieldId);
}
