package uz.cosinus.thinkstore.service.orderService;

import uz.cosinus.thinkstore.dto.createDto.OrderCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderResponseDto;
import uz.cosinus.thinkstore.entity.OrderEntity;
import uz.cosinus.thinkstore.enums.OrderStatus;

import java.util.UUID;

public interface OrderService {
    OrderResponseDto add(OrderCreateDto dto, UUID currentUserId);
    OrderResponseDto getById(UUID orderId);
    OrderResponseDto cancel(UUID orderId);
    OrderResponseDto update(UUID orderId, OrderCreateDto dto);

    OrderEntity findById(UUID orderId);

    String updateStatus(UUID orderId, OrderStatus status, UUID currentUser);
}
