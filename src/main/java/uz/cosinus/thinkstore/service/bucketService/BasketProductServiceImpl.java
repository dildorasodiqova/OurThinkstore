package uz.cosinus.thinkstore.service.bucketService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.BasketProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.BasketProductResponseDto;
import uz.cosinus.thinkstore.entity.BasketProductEntity;
import uz.cosinus.thinkstore.entity.ProductEntity;
import uz.cosinus.thinkstore.entity.UserEntity;
import uz.cosinus.thinkstore.exception.DataNotEnoughException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.BasketProductRepository;
import uz.cosinus.thinkstore.repository.ProductRepository;
import uz.cosinus.thinkstore.service.userService.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketProductServiceImpl implements BasketProductService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final BasketProductRepository basketProductRepository;

    @Override
    public BasketProductResponseDto create(BasketProductCreateDto dto, UUID userId) {
        UserEntity user = userService.findById(userId);
        ProductEntity product = productRepository.findById(dto.getProductId()).orElseThrow(()->new DataNotFoundException("Product not found"));
        if (product.getCount() < dto.getCount()) {
            throw new DataNotEnoughException("Product is not enough.  There are" + product.getCount() + " products");
        }
        product.setCount(product.getCount() - dto.getCount());
        productRepository.save(product);

        BasketProductEntity save = basketProductRepository.save(new BasketProductEntity(user, product, dto.getCount()));
        return parse(save);
    }



    @Override
    public List<BasketProductResponseDto> getAll(int page, int size) {
        Page<BasketProductEntity> all = basketProductRepository.findAllByIsActiveTrue(PageRequest.of(page, size));
           return parse(all.getContent());
    }

    public List<BasketProductResponseDto> getUserProduct(UUID userId) {
        List<BasketProductEntity> all = basketProductRepository.findAllByIsActiveTrueAndUserId(userId);
        return parse(all);
    }

    @Override
    public String updateProductCount(UUID productId, UUID userId, int count) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found !"));
        if (productEntity.getCount() < count){
            throw new DataNotEnoughException("There is not enough product left .");
        }
        basketProductRepository.updateCount(userId, productId, count);
        return "Successfully";
    }



    private BasketProductResponseDto parse(BasketProductEntity basket){
        return  new BasketProductResponseDto(basket.getId(), basket.getProduct().getId(),basket.getProduct().getName(), basket.getProduct().getPrice(), basket.getCount());
    }


    private List<BasketProductResponseDto> parse(List<BasketProductEntity> all) {
        List<BasketProductResponseDto> list = new ArrayList<>();
        for (BasketProductEntity basket : all) {
            list.add(new BasketProductResponseDto(basket.getId(),
                    basket.getProduct().getId(),
                    basket.getProduct().getName(),
                    basket.getProduct().getPrice(),
                    basket.getCount()));
        }
        return list;

    }

}
