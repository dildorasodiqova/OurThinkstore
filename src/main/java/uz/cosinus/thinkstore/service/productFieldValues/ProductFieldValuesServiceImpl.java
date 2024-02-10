package uz.cosinus.thinkstore.service.productFieldValues;

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
import uz.cosinus.thinkstore.repository.ProductFieldsRepository;
import uz.cosinus.thinkstore.repository.ProductRepository;
import uz.cosinus.thinkstore.service.productFieldsService.ProductFieldsService;
import uz.cosinus.thinkstore.service.productService.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductFieldValuesServiceImpl implements ProductFieldValuesService{
    private final ProductFieldValuesRepository productFieldValuesRepository;
    private final ProductFieldsRepository productFieldsRepository;
    private final ProductRepository productRepository;
    @Override
    public String create(List<ProductFieldValuesCreateDto> dtos) {
        for (ProductFieldValuesCreateDto cr : dtos) {
            ProductFieldValues parse = parse(cr);
            productFieldValuesRepository.save(parse);
        }
        return "Successfully";
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

    @Override
    public List<ProductFieldValuesResponseDto> findAllByFieldId(UUID fieldId) {
        List<ProductFieldValues> list = productFieldValuesRepository.findAllByProductFieldsId(fieldId);
        return parse(list);
    }


    private ProductFieldValues parse(ProductFieldValuesCreateDto dto){
        ProductFields productFields = productFieldsRepository.findById(dto.getProductFieldId()).orElseThrow(()-> new DataNotFoundException("Field not found !"));
        ProductEntity product = productRepository.findById(dto.getProductId()).orElseThrow(()-> new DataNotFoundException("Product not found !"));
        return new ProductFieldValues(dto.getValue(), productFields, product);
    }


    private ProductFieldValuesResponseDto parse(ProductFieldValues values){
        return new ProductFieldValuesResponseDto(
                values.getId(),
                values.getValue(),
                values.getProductFields().getId(),
                values.getProduct().getId(),
                values.getCreatedDate().toLocalDateTime());
    }

    private List<ProductFieldValuesResponseDto> parse(List<ProductFieldValues> all){
        List<ProductFieldValuesResponseDto> list = new ArrayList<>();
        for (ProductFieldValues values : all) {
            list.add(new ProductFieldValuesResponseDto(
                    values.getId(),
                    values.getValue(),
                    values.getProductFields().getId(),
                    values.getProduct().getId(),
                    values.getCreatedDate().toLocalDateTime()));
        }
        return list;
    }
}
