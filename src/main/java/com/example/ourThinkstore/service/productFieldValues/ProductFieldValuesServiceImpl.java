package com.example.ourThinkstore.service.productFieldValues;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.ProductFieldValuesCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductFieldValuesResponseDto;
import uz.cosinus.thinkstore.entity.ProductEntity;
import uz.cosinus.thinkstore.entity.ProductFieldValues;
import uz.cosinus.thinkstore.entity.ProductFields;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.ProductFieldValuesRepository;
import uz.cosinus.thinkstore.service.productFieldsService.ProductFieldsService;
import uz.cosinus.thinkstore.service.productService.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductFieldValuesServiceImpl implements ProductFieldValuesService{
    private final ProductFieldValuesRepository productFieldValuesRepository;
    private final ProductFieldsService productFieldsService;
    private final ProductService productService;
    @Override
    public ProductFieldValuesResponseDto create(ProductFieldValuesCreateDto dto) {
        /**
         * buyerda valueni hech nimani tekshirmasdan qoshish kkmi
         */
        ProductFieldValues parse = parse(dto);
        productFieldValuesRepository.save(parse);
        return parse(parse);
    }

    @Override
    public ProductFieldValues findById(UUID productFieldId) {
        return productFieldValuesRepository.findById(productFieldId).orElseThrow(()-> new DataNotFoundException("Product field value not found !"));
    }

    @Override
    public ProductFieldValuesResponseDto getById(UUID productFieldId) {
        ProductFieldValues values = productFieldValuesRepository.findById(productFieldId).orElseThrow(() -> new DataNotFoundException("Product field value not found !"));
        return parse(values);

    }

    @Override
    public ProductFieldValuesResponseDto updateActive(UUID productFieldId, Boolean trueOrFalse) {
        ProductFieldValues values = productFieldValuesRepository.findById(productFieldId).orElseThrow(() -> new DataNotFoundException("Product field value not found !"));
        values.setIsActive(trueOrFalse);
        productFieldValuesRepository.save(values);
        return parse(values);
    }

    @Override
    public List<ProductFieldValuesResponseDto> getALl(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductFieldValues> all = productFieldValuesRepository.findAllByIsActiveTrue(pageRequest);
        return parse(all.getContent());
    }


    private ProductFieldValues parse(ProductFieldValuesCreateDto dto){
        ProductFields productFields = productFieldsService.findById(dto.getProductFieldId());
        ProductEntity product = productService.findById(dto.getProductId());
        return new ProductFieldValues(dto.getValue(), productFields, product);
    }
    private ProductFieldValuesResponseDto parse(ProductFieldValues values){
        return new ProductFieldValuesResponseDto(
                values.getId(),
                values.getValue(),
                values.getProductFields().getId(),
                values.getProduct().getId(),
                values.getCreatedDate());
    }

    private List<ProductFieldValuesResponseDto> parse(List<ProductFieldValues> all){
        List<ProductFieldValuesResponseDto> list = new ArrayList<>();
        for (ProductFieldValues values : all) {
            list.add(new ProductFieldValuesResponseDto(
                    values.getId(),
                    values.getValue(),
                    values.getProductFields().getId(),
                    values.getProduct().getId(),
                    values.getCreatedDate()));
        }
        return list;
    }
}
