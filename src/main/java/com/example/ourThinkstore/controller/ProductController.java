package com.example.ourThinkstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.createDto.ProductCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.ProductResponseDto;
import uz.cosinus.thinkstore.service.productService.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ProductResponseDto> add(@RequestBody ProductCreateDto dto){
        return ResponseEntity.ok(productService.create(dto));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable UUID productId, @RequestParam ProductCreateDto dto){
        return ResponseEntity.ok(productService.update(productId, dto));
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/getById/{productId}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/all-by-Category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> getAllByCategory(
            @PathVariable UUID categoryId,
            @RequestParam(value = "page", defaultValue = "0")
            int page,
            @RequestParam(value = "size", defaultValue = "5")
            int size
    ) {
        return ResponseEntity.ok(productService.getALlByCategory(page, size ,  categoryId));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateActive/{productId}")
    public ResponseEntity<ProductResponseDto>  updateActive(@PathVariable UUID productId, @RequestParam String trueOrFalse){
        return ResponseEntity.ok(productService.updateActive(productId, Boolean.valueOf(trueOrFalse)));
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponseDto>>getAll(
            @RequestParam String word,
            @PathVariable UUID categoryId,
            @RequestParam(value = "page", defaultValue = "0")
            int page,
            @RequestParam(value = "size", defaultValue = "5")
            int size){
        return ResponseEntity.ok(productService.searchByNameOrCategoryName(word, page, size));
    }



}
