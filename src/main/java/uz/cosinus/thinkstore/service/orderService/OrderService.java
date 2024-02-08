package uz.cosinus.thinkstore.service.orderService;

import uz.cosinus.thinkstore.dto.createDto.OrderCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderResponseDto;

import java.util.UUID;

public interface OrderService {
    OrderResponseDto add(OrderCreateDto dto);
    OrderResponseDto getById(UUID orderId);
    OrderResponseDto cancel(UUID orderId);
    OrderResponseDto update(UUID orderId, OrderCreateDto dto);
}
