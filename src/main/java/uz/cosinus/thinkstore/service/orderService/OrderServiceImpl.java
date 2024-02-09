package uz.cosinus.thinkstore.service.orderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.OrderCreateDto;
import uz.cosinus.thinkstore.dto.createDto.OrderProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderProductResponseDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderResponseDto;
import uz.cosinus.thinkstore.entity.OrderEntity;
import uz.cosinus.thinkstore.entity.OrderProductEntity;
import uz.cosinus.thinkstore.entity.UserEntity;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.OrderProductRepository;
import uz.cosinus.thinkstore.repository.OrderRepository;
import uz.cosinus.thinkstore.repository.UserRepository;
import uz.cosinus.thinkstore.service.orderProductService.OrderProductService;

import java.util.List;
import java.util.UUID;

import static uz.cosinus.thinkstore.enums.OrderStatus.CANCELLED;
import static uz.cosinus.thinkstore.enums.OrderStatus.NEW;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private final OrderProductService orderProductService;

    @Override
    public OrderResponseDto add(OrderCreateDto dto, UUID currentUserId) {
        UserEntity user = userRepository.findById(currentUserId).orElseThrow(() -> new DataNotFoundException("User not found"));

        double price = 0;
        for (OrderProductCreateDto product : dto.getProducts()) {
            price += product.getPrice();
        }

        OrderEntity order = orderRepository.save(new OrderEntity(user, price, NEW, false));
        List<OrderProductResponseDto> save = orderProductService.save(order, dto.getProducts());
        return parse(order, save);
    }

    @Override
    public OrderResponseDto getById(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found "));
        List<OrderProductEntity> orderProducts = order.getOrderProducts();

        List<OrderProductResponseDto> parse = orderProductService.parse(orderProducts);
        return new OrderResponseDto(order.getUser().getId(), order.getPrice(), parse);
    }

    @Override
    public OrderResponseDto cancel(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        orderRepository.updateStatus(order.getId(), CANCELLED);
        List<OrderProductResponseDto> parse = orderProductService.parse(order.getOrderProducts());
        return parse(order, parse);
    }

    @Override
    public OrderResponseDto update(UUID orderId, OrderCreateDto dto) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        double price = 0;
        for (OrderProductCreateDto product : dto.getProducts()) {
            price += product.getPrice();
        }
        order.setPrice(price);
        orderRepository.save(order);
        List<OrderProductResponseDto> update = orderProductService.update(dto.getProducts(), order);
        return parse(order, update);
    }

    private OrderResponseDto parse(OrderEntity order, List<OrderProductResponseDto> save) {
        return new OrderResponseDto(order.getUser().getId(), order.getPrice(), save);
    }
}
