package uz.cosinus.thinkstore.service.productService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.cosinus.thinkstore.dto.createDto.ProductCreateDto;
import uz.cosinus.thinkstore.dto.createDto.ProductFieldValuesCreateDto;
import uz.cosinus.thinkstore.dto.createDto.ProductFieldsCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.CategoryResponseDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductFieldsResponseDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductResponseDto;
import uz.cosinus.thinkstore.entity.AttachmentEntity;
import uz.cosinus.thinkstore.entity.CategoryEntity;
import uz.cosinus.thinkstore.entity.ProductEntity;
import uz.cosinus.thinkstore.entity.ProductPhotosEntity;
import uz.cosinus.thinkstore.exception.DataAlreadyExistsException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.AttachmentRepository;
import uz.cosinus.thinkstore.repository.ProductRepository;
import uz.cosinus.thinkstore.service.categoryService.CategoryService;
import uz.cosinus.thinkstore.service.productFieldValues.ProductFieldValuesService;
import uz.cosinus.thinkstore.service.productFieldsService.ProductFieldsService;
import uz.cosinus.thinkstore.service.productPhotosService.ProductPhotosService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AttachmentRepository attachmentRepository;
    private final ProductPhotosService productPhotosService;
    private final ProductFieldsService productFieldsService;
    private final ModelMapper modelMapper;


    @Transactional
    @Override
    public ProductResponseDto create(ProductCreateDto dto) {
        return createProduct(dto);
    }


    @Transactional
    public ProductResponseDto createProduct(ProductCreateDto dto) {
        if (productRepository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new DataAlreadyExistsException("Product already exists");
        }
        CategoryEntity category = categoryService.findById(dto.getCategoryId());

        List<CategoryResponseDto> subList = categoryService.subCategories(category.getId());

        if(!subList.isEmpty()){
            throw new uz.cosinus.thinkstore.exception.BadRequestException("You cannot save products to this category. please save to sub categories. ");
        }

        ProductEntity map = modelMapper.map(dto, ProductEntity.class);
        map.setCategory(category);

        ProductEntity save = productRepository.save(map);

        productFieldsService.create( save.getId(),  dto.getProductFields());

        List<AttachmentEntity> photos = attachmentRepository.findAllById(dto.getPhotos());
        List<ProductPhotosEntity> list = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            ProductPhotosEntity productPhotos = new ProductPhotosEntity();
            productPhotos.setProduct(save);
            productPhotos.setPhoto(photos.get(i));
            productPhotos.setOrderIndex(i);
            productPhotosService.save(productPhotos);
            list.add(productPhotos);
        }

        List<UUID> photosId = getPhotosId(list);

        ProductResponseDto product = modelMapper.map(save, ProductResponseDto.class);
        product.setId(save.getId());
        product.setPhotos(photosId);
        product.setCategory(categoryService.getById(save.getCategory().getId()));

        return product;
    }


    @Override
    public ProductEntity findById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(()-> new DataAlreadyExistsException("Product not found !"));
    }

    @Transactional
    @Override
    public ProductResponseDto updateActive(UUID productId, Boolean trueOrFalse) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found !"));
        product.setIsActive(trueOrFalse);
        productRepository.save(product);
        return parse(product);
    }


    @Transactional
    public List<UUID> getPhotosId(List<ProductPhotosEntity> productPhotos) {
        return  productPhotos.stream().map(productPhotosEntity -> productPhotosEntity.getPhoto().getId()).toList();
    }


    @Override
    public ProductResponseDto getById(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found !"));
        return parse(product);
    }


    @Transactional
    @Override
    public ProductResponseDto update(UUID productId, ProductCreateDto dto) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found !"));
        CategoryEntity category = categoryService.findById(dto.getCategoryId());

        product.setCategory(category);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setBrand(dto.getBrand());
        product.setCount(dto.getCount());
        product.setDescription(dto.getDescription());


        productRepository.save(product);

        return parse(product);
    }

    @Override
    public List<ProductResponseDto> getALlByCategory(int page, int size, UUID categoryId) {
        List<ProductEntity> all = productRepository.findAllByCategoryId(categoryId);
        return parse(all);
    }

    @Override
    public List<ProductResponseDto> searchByNameOrCategoryName(String word, int page, int size) {
        Page<ProductEntity> list = productRepository.searchAllByNameOrCategoryName(word, PageRequest.of(page, size));
        return parse(list.stream().toList());
    }


    private ProductResponseDto parse(ProductEntity product){
        ProductResponseDto map = modelMapper.map(product, ProductResponseDto.class);
        map.setId(product.getId());
        map.setCreatedDate(product.getCreatedDate().toLocalDateTime());
        /// uyerda rasmlar qob ketdi
        map.setCategory(categoryService.getById(product.getCategory().getId()));

        return map;
    }
    private List<ProductResponseDto> parse(List<ProductEntity> products){
        List<ProductResponseDto> list = new ArrayList<>();
        for (ProductEntity product : products) {
            CategoryResponseDto dto = categoryService.getById(product.getCategory().getId());
            List<UUID> photos = productPhotosService.getPhotosOfProduct(product.getId());
           List<ProductFieldsResponseDto> list1 =  productFieldsService.getFieldsOfProducts(product.getId());
            ProductResponseDto response = new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCount(),
                    dto,
                    photos,
                    product.getBrand(),
                    product.getCreatedDate().toLocalDateTime(),
                    list1
                    );

            list.add(response);
        }
        return list;
    }

}
