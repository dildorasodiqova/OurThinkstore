package com.example.ourThinkstore.service.orderProductService;

import uz.cosinus.thinkstore.dto.createDto.OrderProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderProductResponseDto;
import uz.cosinus.thinkstore.entity.OrderEntity;
import uz.cosinus.thinkstore.entity.OrderProductEntity;

import java.util.List;

public interface OrderProductService {
    List<OrderProductResponseDto> save(OrderEntity order, List<OrderProductCreateDto> products);
    List<OrderProductResponseDto> parse(List<OrderProductEntity> products);
    List<OrderProductResponseDto> update(List<OrderProductCreateDto> products, OrderEntity order);
}
