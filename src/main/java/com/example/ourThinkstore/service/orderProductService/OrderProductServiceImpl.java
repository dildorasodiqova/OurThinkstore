package com.example.ourThinkstore.service.orderProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.OrderProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderProductResponseDto;
import uz.cosinus.thinkstore.entity.OrderEntity;
import uz.cosinus.thinkstore.entity.OrderProductEntity;
import uz.cosinus.thinkstore.entity.ProductEntity;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.OrderProductRepository;
import uz.cosinus.thinkstore.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderProductResponseDto> save(OrderEntity order, List<OrderProductCreateDto> products) {
        List<OrderProductEntity> pr = products.stream().map(item -> {
            ProductEntity product = productRepository.findById(item.getProductId()).orElseThrow(() -> new DataNotFoundException("Product not found"));
            product.setCount(product.getCount() - item.getCount());
            productRepository.save(product);
            return new OrderProductEntity(order, product, item.getCount(), item.getPrice());
        }).toList();
        orderProductRepository.saveAll(pr);
        return parse(pr);
    }

    public List<OrderProductResponseDto> parse(List<OrderProductEntity> products) {
        return products
                .stream()
                .map(item -> new OrderProductResponseDto(item.getOrder().getId(), item.getProduct().getName(), item.getCount(), item.getPrice()))
                .toList();
    }

    @Override
    public List<OrderProductResponseDto> update(List<OrderProductCreateDto> products, OrderEntity order) {
        List<OrderProductEntity> orderProducts = order.getOrderProducts();
        List<UUID> oldProducts = orderProducts.stream().map(OrderProductEntity::getId).toList();
        List<UUID> newProducts = products.stream().map(OrderProductCreateDto::getProductId).toList();
        List<OrderProductEntity> saveAll = new ArrayList<>();
        products.forEach(item -> {
            if (!oldProducts.contains(item.getProductId())){
                ProductEntity product = productRepository.findById(item.getProductId()).orElseThrow(() -> new DataNotFoundException("Product not found"));
                saveAll.add(new OrderProductEntity(order, product, item.getCount(), item.getPrice()));
            }else {
                Optional<OrderProductEntity> first = orderProducts.stream().filter(product -> product.getProduct().getId().equals(item.getProductId())).findFirst();
               if (first.isPresent()){
                   OrderProductEntity orderProduct = first.get();
                   orderProduct.setCount(item.getCount());
                   orderProduct.setPrice(item.getPrice());
                   saveAll.add(orderProduct);
               }
            }
        });
        List<OrderProductEntity> deleteProducts = orderProducts.stream().filter(item -> !newProducts.contains(item.getId())).toList();
        orderProductRepository.deleteAll(deleteProducts);
        List<OrderProductEntity> list = orderProductRepository.saveAll(saveAll);
        return parse(list);
    }


}
