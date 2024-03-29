package uz.cosinus.thinkstore.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.createDto.BasketProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.BasketProductResponseDto;
import uz.cosinus.thinkstore.service.bucketService.BasketProductService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/basket")
public class BasketProductController {
    private final BasketProductService basketProductService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add")
    public ResponseEntity<BasketProductResponseDto> add(@RequestBody BasketProductCreateDto dto, Principal principal){
        return ResponseEntity.ok(basketProductService.create(dto, UUID.fromString(principal.getName())));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/basketsOfUser")
    public ResponseEntity<List<BasketProductResponseDto>> basketsOfUser(Principal principal){
        return ResponseEntity.ok(basketProductService.getUserProduct(UUID.fromString(principal.getName())));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/basketController/updateCount/{productId}")
    public ResponseEntity<String> updateCount(@PathVariable UUID productId,@RequestParam int count, Principal principal){
        return ResponseEntity.ok(basketProductService.updateProductCount(productId,UUID.fromString(principal.getName()), count));
    }

    @PermitAll
    @GetMapping("/get-all")
    public List<BasketProductResponseDto> getAll(
            @RequestParam(value = "page", defaultValue = "0")
            int page,
            @RequestParam(value = "size", defaultValue = "5")
            int size
    ) {
        return basketProductService.getAll(page, size);
    }





}
