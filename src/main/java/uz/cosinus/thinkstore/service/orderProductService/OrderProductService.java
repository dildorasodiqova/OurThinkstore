package uz.cosinus.thinkstore.service.orderProductService;

import uz.cosinus.thinkstore.dto.createDto.OrderProductCreateDto;
import uz.cosinus.thinkstore.dto.createDto.OrderProductsCreate;
import uz.cosinus.thinkstore.dto.responseDto.OrderProductResponseDto;
import uz.cosinus.thinkstore.entity.OrderEntity;
import uz.cosinus.thinkstore.entity.OrderProductEntity;

import java.util.List;

public interface OrderProductService {
    List<OrderProductResponseDto> save(OrderEntity order, List<OrderProductsCreate> products);
    List<OrderProductResponseDto> parse(List<OrderProductEntity> products);
    List<OrderProductResponseDto> update(List<OrderProductsCreate> products, OrderEntity order);
}
