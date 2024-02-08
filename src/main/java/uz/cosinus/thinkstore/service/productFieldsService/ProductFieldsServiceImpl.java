package uz.cosinus.thinkstore.service.productFieldsService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.ProductFieldsCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductFieldsResponseDto;
import uz.cosinus.thinkstore.entity.ProductFields;
import uz.cosinus.thinkstore.exception.BadRequestException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.ProductFieldsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductFieldsServiceImpl implements ProductFieldsService {
    private final ProductFieldsRepository productFieldsRepository;
    @Override
    public ProductFieldsResponseDto create(ProductFieldsCreateDto dto) {
        if (productFieldsRepository.existsAllByProductIdAndName(dto.getProductId(), dto.getName())){
            throw new BadRequestException("This field name already exists in product .");
        }
        ProductFields parse = parse(dto);
        productFieldsRepository.save(parse);
        return parse(parse);
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
        return new ProductFieldsResponseDto(field.getId(), ); /// shunda 2 chi fieldni qanday topay
    }

    private ProductFields parse(ProductFieldsCreateDto dto){
        return new ProductFields(); // buyerdayam rasm un toldirmadim
    }

    private List<ProductFieldsResponseDto> parse(Page<ProductFields> all){
        List<ProductFieldsResponseDto> list  = new ArrayList<>();
        for (ProductFields fields : all) {
            // buyerga tepadagini yozsak shuni kochirib qoyamiz
        }
        return list;
    }
}
