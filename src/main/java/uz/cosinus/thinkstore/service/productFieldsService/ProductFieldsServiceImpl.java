package uz.cosinus.thinkstore.service.productFieldsService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.ProductFieldsCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductFieldValuesResponseDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductFieldsResponseDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductResponseDto;
import uz.cosinus.thinkstore.entity.ProductEntity;
import uz.cosinus.thinkstore.entity.ProductFieldValues;
import uz.cosinus.thinkstore.entity.ProductFields;
import uz.cosinus.thinkstore.exception.BadRequestException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.ProductFieldsRepository;
import uz.cosinus.thinkstore.service.productFieldValues.ProductFieldValuesService;
import uz.cosinus.thinkstore.service.productService.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductFieldsServiceImpl implements ProductFieldsService {
    private final ProductFieldsRepository productFieldsRepository;
    private final ProductFieldValuesService productFieldValuesService;
    private final ProductService productService;
    @Override
    public String create(UUID productID, List<ProductFieldsCreateDto> dto) {
        for (ProductFieldsCreateDto cr : dto) {
            if (productFieldsRepository.existsAllByProductIdAndName(productID, cr.getName())){
                throw new BadRequestException("This field name already exists in product .");
            }
            ProductFields parse = parse(cr);
            productFieldsRepository.save(parse);
            productFieldValuesService.create(cr.getProductFieldValues());
        }


        return "";
    }

    @Override
    public ProductFields findById(UUID productFieldId) {
        return  productFieldsRepository.findById(productFieldId).orElseThrow(() -> new DataNotFoundException("Field not found !"));

    }

    @Override
    public ProductFieldsResponseDto getById(UUID productFieldId) {
        ProductFields field = productFieldsRepository.findById(productFieldId).orElseThrow(() -> new DataNotFoundException("Field not found !"));
        return parse(field);
    }

    @Override
    public ProductFieldsResponseDto updateActive(UUID productFieldId, Boolean trueOrFalse) {
        ProductFields field = productFieldsRepository.findById(productFieldId).orElseThrow(() -> new DataNotFoundException("Field not found !"));
        field.setIsActive(trueOrFalse);
        productFieldsRepository.save(field);
        return parse(field);
    }

    @Override
    public List<ProductFieldsResponseDto> getALl(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductFields> all = productFieldsRepository.findAllByIsActiveTrue(pageRequest);
        return parse(all);
    }

    private ProductFieldsResponseDto parse(ProductFields field){
        List<ProductFieldValuesResponseDto> all = productFieldValuesService.findAllByFieldId(field.getId());
        ProductResponseDto product = productService.getById(field.getProduct().getId());
        return new ProductFieldsResponseDto(field.getId(), all, field.getName(), product, field.getCreatedDate()); /// shunda 2 chi fieldni qanday topay
    }

    private ProductFields parse(ProductFieldsCreateDto dto){
        ProductEntity product = productService.findById(dto.getProductId());
        return new ProductFields(dto.getName(),product);
    }

    private List<ProductFieldsResponseDto> parse(Page<ProductFields> all){
        List<ProductFieldsResponseDto> list  = new ArrayList<>();
        for (ProductFields fields : all) {
            List<ProductFieldValuesResponseDto> all1 = productFieldValuesService.findAllByFieldId(fields.getId());
            ProductResponseDto pr = productService.getById(fields.getProduct().getId());
            list.add(new ProductFieldsResponseDto(fields.getId(), all1, fields.getName(),pr, fields.getCreatedDate()));
        }
        return list;
    }
}
