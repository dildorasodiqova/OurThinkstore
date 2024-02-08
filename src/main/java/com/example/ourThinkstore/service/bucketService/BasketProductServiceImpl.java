package com.example.ourThinkstore.service.bucketService;

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
    public BasketProductResponseDto create(BasketProductCreateDto dto) {
        UserEntity user = userService.findById(dto.getUserId());
        ProductEntity product = productRepository.findById(dto.getProductId()).orElseThrow(()->new DataNotFoundException("Product not found"));
        if (product.getCount() < dto.getCount()) {
            throw new DataNotEnoughException("Product is not enough.  There are" + product.getCount() + " products");
        }
        BasketProductEntity save = basketProductRepository.save(new BasketProductEntity(user, product, dto.getCount()));
        return parse(save);
    }

    @Override
    public BasketProductResponseDto getById(UUID basketId) {
        BasketProductEntity basket = basketProductRepository.findById(basketId).orElseThrow(()-> new DataNotFoundException("Basket not found !"));
        return parse(basket);
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
    public BasketProductResponseDto updateProductCount(UUID productId, UUID userId, int count) {
            BasketProductEntity basketProduct = basketProductRepository.updateCount(userId, productId, count);
        return parse(basketProduct);
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
