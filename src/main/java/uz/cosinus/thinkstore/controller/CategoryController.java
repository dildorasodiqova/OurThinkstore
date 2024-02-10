package uz.cosinus.thinkstore.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.createDto.CategoryCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.CategoryResponseDto;
import uz.cosinus.thinkstore.service.categoryService.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryCreateDto createDTO) {
        return ResponseEntity.ok(categoryService.create(createDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable UUID categoryId, @RequestBody CategoryCreateDto dto){
        return ResponseEntity.ok(categoryService.update(categoryId, dto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateActive/{categoryId}")
    public ResponseEntity<CategoryResponseDto>  updateActive(@PathVariable UUID categoryId, @RequestParam String trueOrFalse){
        return ResponseEntity.ok(categoryService.updateActive(categoryId, Boolean.valueOf(trueOrFalse)));
    }

    @PermitAll
    @GetMapping("/firstCategory")
    public ResponseEntity<List<CategoryResponseDto>> firstCategory(){
        return ResponseEntity.ok(categoryService.firstCategories());
    }

    @PermitAll
    @GetMapping("getById/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable UUID categoryId){
        return ResponseEntity.ok(categoryService.getById(categoryId));
    }

    @PermitAll
    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryResponseDto>> getAll(
            @RequestParam String word,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(categoryService.getAll(word, page,size));
    }

    @PermitAll
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/sub/{categoryId}")
    public ResponseEntity<List<CategoryResponseDto>> getSubCategories(@PathVariable UUID categoryId){
        return ResponseEntity.ok(categoryService.subCategories(categoryId));
    }

}
