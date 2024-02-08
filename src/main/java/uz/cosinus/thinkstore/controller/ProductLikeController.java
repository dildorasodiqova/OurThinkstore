package uz.cosinus.thinkstore.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.createDto.ProductLikeCreateDto;
import uz.cosinus.thinkstore.service.productLikeService.ProductLikeService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productLike")
public class ProductLikeController {
    private final ProductLikeService productLikeService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ProductLikeCreateDto dto){
        return ResponseEntity.ok(productLikeService.create(dto));
    }

    @PermitAll
    @GetMapping("/countLikeOfProduct/{productId}")
    public ResponseEntity<Long> countLikeOfProduct(@PathVariable UUID productId){
        return ResponseEntity.ok(productLikeService.getALlLike(productId));
    }

}
