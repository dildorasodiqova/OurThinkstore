package uz.cosinus.thinkstore.service.categoryService;


import uz.cosinus.thinkstore.dto.createDto.CategoryCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.CategoryResponseDto;
import uz.cosinus.thinkstore.entity.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto getById(UUID categoryId);
    List<CategoryResponseDto> getAll(String word , int page, int size);

    CategoryResponseDto create(CategoryCreateDto createDTO);
    CategoryEntity findById(UUID categoryId);

    List<CategoryResponseDto> firstCategories();
    List<CategoryResponseDto> subCategories(UUID parentId);

    CategoryResponseDto update(UUID categoryId, CategoryCreateDto dto);

    CategoryResponseDto updateActive(UUID categoryId, Boolean aBoolean);
}
