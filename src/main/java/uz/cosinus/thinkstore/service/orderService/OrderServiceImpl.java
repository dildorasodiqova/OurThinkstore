package uz.cosinus.thinkstore.service.orderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.OrderCreateDto;
import uz.cosinus.thinkstore.dto.createDto.OrderProductCreateDto;
import uz.cosinus.thinkstore.dto.createDto.OrderProductsCreate;
import uz.cosinus.thinkstore.dto.createDto.TransactionCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderProductResponseDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderResponseDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductResponseDto;
import uz.cosinus.thinkstore.entity.OrderEntity;
import uz.cosinus.thinkstore.entity.OrderProductEntity;
import uz.cosinus.thinkstore.entity.ProductEntity;
import uz.cosinus.thinkstore.entity.UserEntity;
import uz.cosinus.thinkstore.enums.OrderStatus;
import uz.cosinus.thinkstore.exception.BadRequestException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.OrderProductRepository;
import uz.cosinus.thinkstore.repository.OrderRepository;
import uz.cosinus.thinkstore.repository.ProductRepository;
import uz.cosinus.thinkstore.repository.UserRepository;
import uz.cosinus.thinkstore.service.orderProductService.OrderProductService;
import uz.cosinus.thinkstore.service.productService.ProductService;
import uz.cosinus.thinkstore.service.transactionService.TransactionService;
import uz.cosinus.thinkstore.service.transactionService.TransactionServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.cosinus.thinkstore.enums.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderProductService orderProductService;
    private final ProductRepository productRepository;
    private final TransactionService transactionService;

    @Override
    public OrderResponseDto add(OrderCreateDto dto, UUID currentUserId) {
        UserEntity user = userRepository.findById(currentUserId).orElseThrow(() -> new DataNotFoundException("User not found"));

        List<OrderProductsCreate> list = new ArrayList<>();
        List<ProductEntity> products = productRepository.findAllById(dto.getProducts().stream().map(OrderProductsCreate::getProductId).toList());
        OrderEntity order = new OrderEntity(user, products.stream().mapToDouble(ProductEntity::getPrice).sum(),CREATED,false); /// nima

        for (OrderProductsCreate product : dto.getProducts()) {
           ProductEntity productEntity = products.stream().filter(item->item.getId().equals(product.getProductId())).findAny().orElseThrow(()-> new DataNotFoundException("Product not found !"));
            if (productEntity.getCount() <= product.getCount()) {
                productEntity.setCount(productEntity.getCount() - product.getCount());
                productRepository.save(productEntity);       /// productni countini kamaytirib qoyayapmiz
            }
        }
        orderRepository.save(order);
        List<OrderProductResponseDto> save = orderProductService.save(order, list);
        transactionService.transaction(new TransactionCreateDto(order.getId(), order.getPrice(), dto.getPaymentType()), currentUserId);


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
        List<OrderProductEntity> orderProducts = order.getOrderProducts();
        for (OrderProductEntity orderProduct : orderProducts) {
            order.setPrice(orderProduct.getProduct().getPrice());
        }
        orderRepository.save(order);
        List<OrderProductResponseDto> update = orderProductService.update(dto.getProducts(), order);
        return parse(order, update);
    }

    @Override
    public OrderEntity findById(UUID orderId) {
       return orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
    }

    @Override
    public String updateStatus(UUID orderId, OrderStatus status, UUID currentUser) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        if (order.getStatus().equals(status)) {
            throw new BadRequestException("You cannot change the status !");
        }
        switch (status) {
            case IN_PROGRESS -> {
                if (order.getStatus().equals(CREATED))
                    orderRepository.updateStatus(orderId, IN_PROGRESS);
                else
                    throw new BadRequestException("You cannot change the status !");
            }
            case DELIVERED -> {
                if (order.getStatus().equals(IN_PROGRESS))
                    orderRepository.updateStatus(orderId, DELIVERED);
                else
                    throw new BadRequestException("You cannot change the status !");
            }
            case APPROVED -> {
                if (order.getStatus().equals(DELIVERED))
                    orderRepository.updateStatus(orderId, APPROVED);
                else
                    throw new BadRequestException("You cannot change the status !");
            }
            case CANCELLED -> {
                // transaction statusni ham o'zgartirish kerak


                for (OrderProductEntity orderProduct : order.getOrderProducts()) {   // sotib olgan productni orqaga qaytarish
                    ProductEntity product = orderProduct.getProduct();
                    product.setCount(product.getCount() + orderProduct.getCount());
                    productRepository.save(product);
                }
                orderRepository.updateStatus(orderId, CANCELLED);
            }
            default -> {
                throw new BadRequestException("You cannot change the status !");
            }
        }
        return "Successfully";
    }


    private OrderResponseDto parse(OrderEntity order, List<OrderProductResponseDto> save) {
        return new OrderResponseDto(order.getUser().getId(), order.getPrice(), save);
    }

}
