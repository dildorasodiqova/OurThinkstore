package uz.cosinus.thinkstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.createDto.OrderCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.OrderResponseDto;
import uz.cosinus.thinkstore.enums.OrderStatus;
import uz.cosinus.thinkstore.service.orderService.OrderService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize(" hasAuthority('USER')")
    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> create(@RequestBody OrderCreateDto createDto, Principal principal) {
        return ResponseEntity.ok(orderService.add(createDto, UUID.fromString(principal.getName())));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getById/{orderId}")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable UUID orderId){
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderResponseDto> update(@PathVariable UUID orderId,
                                                   @RequestBody OrderCreateDto dto){
        return ResponseEntity.ok(orderService.update(orderId, dto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateStatus/{orderId}")
    public ResponseEntity<String> updateStatus(@PathVariable UUID orderId,@RequestParam String status,  Principal principal){
        return ResponseEntity.ok(orderService.updateStatus(orderId, OrderStatus.valueOf(status), UUID.fromString(principal.getName())));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponseDto> cancel(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.cancel(orderId));
    }






}
