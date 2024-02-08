package com.example.ourThinkstore.dto.createDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryCreateDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private UUID photoId;
    private UUID parentId;
    private String description;

}
