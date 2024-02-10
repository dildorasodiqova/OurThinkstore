package uz.cosinus.thinkstore.service.categoryService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.cosinus.thinkstore.dto.createDto.CategoryCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.CategoryResponseDto;
import uz.cosinus.thinkstore.entity.AttachmentEntity;
import uz.cosinus.thinkstore.entity.CategoryEntity;
import uz.cosinus.thinkstore.exception.DataAlreadyExistsException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.CategoryRepository;
import uz.cosinus.thinkstore.service.attachmentService.AttachmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final AttachmentService attachmentService;

    @Override
    public CategoryResponseDto getById(UUID categoryId) {
        Optional<CategoryEntity> categoryBy = categoryRepository.findById(categoryId);
        if (categoryBy.isEmpty()) {
            throw new DataNotFoundException("Category not found");
        } else {
            CategoryEntity category = categoryBy.get();
            CategoryResponseDto map = modelMapper.map(category, CategoryResponseDto.class);
            map.setParentId(category.getParentId());
            map.setPhotoId(category.getPhoto().getId());
            return map;
        }
    }


    @Override
    public List<CategoryResponseDto> getAll(String word, int page, int size) {
        Page<CategoryEntity> result;
        if (Optional.ofNullable(word).isPresent()) {
            result = categoryRepository.findAllByNameContainingIgnoreCaseAndIsActiveTrueOrderByCreatedDateDesc(word, PageRequest.of(page, size));
        } else {
            Sort sort = Sort.by("createdDate").descending(); // Sort by createdDate in descending order
            result = categoryRepository.findAllByIsActiveTrue(PageRequest.of(page, size, sort));
        }
        return mapToDtoList(result.getContent());
    }

    private List<CategoryResponseDto> mapToDtoList(List<CategoryEntity> entities) {
        List<CategoryResponseDto> categoryDTOS = new ArrayList<>();
        for (CategoryEntity category : entities) {
            CategoryResponseDto map = modelMapper.map(category, CategoryResponseDto.class);
            categoryDTOS.add(map);
        }
        return categoryDTOS;
    }


    @Transactional
    @Override
    public CategoryResponseDto create(CategoryCreateDto createDTO) {
        Optional<CategoryEntity> byName = categoryRepository.getByName(createDTO.getName());
        if (byName.isPresent()) {
            throw new DataAlreadyExistsException("Category name already exists");
        }
        CategoryEntity parentCategory = null;
        if (createDTO.getParentId() != null) {
            parentCategory = categoryRepository.findById(createDTO.getParentId()).orElseThrow(() -> new DataNotFoundException("Category not found"));
        }

        AttachmentEntity photo = null;
        if (createDTO.getPhotoId() != null) {
             photo = attachmentService.findById(createDTO.getPhotoId());
        }

        CategoryEntity category = categoryRepository.save(parse(createDTO));

        CategoryResponseDto response = modelMapper.map(category, CategoryResponseDto.class);
        response.setParentId(parentCategory != null ? parentCategory.getId() : null);
        response.setPhotoId(photo != null ? photo.getId() : null);

        return response;
    }

    @Override
    public CategoryEntity findById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found"));

    }

    @Override
    public List<CategoryResponseDto> firstCategories() {
        List<CategoryEntity> categories = categoryRepository.getFirstCategory();
        List<CategoryResponseDto> list = new ArrayList<>();
        for (CategoryEntity category : categories) {
            if (category.getIsActive()) {
                CategoryResponseDto map = modelMapper.map(category, CategoryResponseDto.class);
                map.setPhotoId(category.getPhoto().getId());
                map.setParentId(null);
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public List<CategoryResponseDto> subCategories(UUID parentId) {
        List<CategoryEntity> categoriesByParentId = categoryRepository.getCategoriesByParentId(parentId);
        return parse(categoriesByParentId);

    }

    @Override
    public CategoryResponseDto update(UUID categoryId, CategoryCreateDto dto) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found"));
        category.setDescription(dto.getDescription());
        category.setName(dto.getName());

        AttachmentEntity photo = attachmentService.findById(dto.getPhotoId());
        category.setPhoto(photo);
        category.setParentId(dto.getParentId());

        return parse(category);
    }

    @Override
    public CategoryResponseDto updateActive(UUID categoryId, Boolean aBoolean) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found"));
        category.setIsActive(aBoolean);
        categoryRepository.save(category);
        return parse(category);
    }

    private List<CategoryResponseDto> parse(List<CategoryEntity> category) {
        List<CategoryResponseDto> list = new ArrayList<>();
        for (CategoryEntity category1 : category) {
            if (category1.getIsActive()) {
                CategoryResponseDto map = modelMapper.map(category1, CategoryResponseDto.class);
                map.setPhotoId(category1.getPhoto().getId());
                map.setParentId(category1.getParentId());
                list.add(map);
            }
        }
        return list;
    }
    private CategoryResponseDto parse(CategoryEntity category){
        return new CategoryResponseDto(category.getId(), category.getName(), category.getPhoto().getId(), category.getParentId(), category.getDescription(), category.getCreatedDate().toLocalDateTime());
    }

    private CategoryEntity parse(CategoryCreateDto createDto) {
        AttachmentEntity attachment = attachmentService.findById(createDto.getPhotoId());
        return new CategoryEntity(createDto.getName(), attachment, createDto.getParentId(), createDto.getDescription());
    }

}
