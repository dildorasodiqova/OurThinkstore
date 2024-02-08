package uz.cosinus.thinkstore.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponseDto {
    private UUID id;
    private String name;
    private UUID photoId;
    private UUID parentId;
    private String description;
    private LocalDateTime createdDate;
}
