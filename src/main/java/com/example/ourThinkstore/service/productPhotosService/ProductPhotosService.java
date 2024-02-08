package com.example.ourThinkstore.service.productPhotosService;

import uz.cosinus.thinkstore.entity.ProductPhotosEntity;

import java.util.List;
import java.util.UUID;

public interface ProductPhotosService {
    List<ProductPhotosEntity> getByProductId(UUID productId);

    ProductPhotosEntity save(ProductPhotosEntity productPhotos);
}
