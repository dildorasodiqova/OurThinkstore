package uz.cosinus.thinkstore.service.feedbackService;

import uz.cosinus.thinkstore.dto.createDto.FeedBackCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.FeedbackResponseDto;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    FeedbackResponseDto create(FeedBackCreateDto dto, UUID userId);
    FeedbackResponseDto findById(UUID feedbackId);
    List<FeedbackResponseDto> feedbacksOfProduct(UUID productId);
    String delete(UUID feedbackId, UUID userId);

    FeedbackResponseDto update(UUID feedbackId, String text, UUID currentUserId);
}
