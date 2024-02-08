package uz.cosinus.thinkstore.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeedbackResponseDto {
    private UUID id;
    private UUID productId;
    private String userName;
    private int rate; /// yulduzchalari
    private String text;
    private LocalDateTime createdDate;
}
