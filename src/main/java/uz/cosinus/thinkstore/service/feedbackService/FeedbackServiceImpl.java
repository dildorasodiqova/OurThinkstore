package uz.cosinus.thinkstore.service.feedbackService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.cosinus.thinkstore.dto.createDto.FeedBackCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.FeedbackResponseDto;
import uz.cosinus.thinkstore.entity.FeedbackEntity;
import uz.cosinus.thinkstore.entity.ProductEntity;
import uz.cosinus.thinkstore.entity.UserEntity;
import uz.cosinus.thinkstore.exception.BadRequestException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.FeedBackRepository;
import uz.cosinus.thinkstore.service.productService.ProductService;
import uz.cosinus.thinkstore.service.userService.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedBackRepository feedBackRepository; /// shularni service bn alishtirish kk
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Override
    public FeedbackResponseDto create(FeedBackCreateDto dto, UUID userId) {
        if (dto.getText().isEmpty() && dto.getRate() == 0) {
            throw  new BadRequestException("Feedback cannot be empty !");
        }
        return parse(feedBackRepository.save(parse(dto, userId)));
    }


    @Override
    public FeedbackResponseDto findById(UUID feedbackId) {
        FeedbackEntity feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found"));
        return parse(feedback);
    }

    @Override
    public List<FeedbackResponseDto> feedbacksOfProduct(UUID productId) {
        List<FeedbackEntity> allByProductId = feedBackRepository.findAllByProductIdOrderByCreatedDateDesc(productId);
        List<FeedbackResponseDto> list = new ArrayList<>();
        for (FeedbackEntity feedback : allByProductId) {
            list.add(parse(feedback));
        }
        return list;

    }

    @Override
    public String delete(UUID feedbackId, UUID userId) {
        FeedbackEntity feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found !"));
        UserEntity byId = userService.findById(userId);
        if (feedback.getUser().getId().equals(userId) || byId.getRole().name().equals("ADMIN") ) {
            feedBackRepository.delete(feedback);
            return "Successfully";
        }
        return "Unauthorized";
    }

    @Override
    public FeedbackResponseDto update(UUID feedbackId, String text, UUID currentUserId) {
        FeedbackEntity feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found !"));
        if (Objects.equals(feedback.getUser().getId(), currentUserId)) {
            feedback.setText(text);
            feedBackRepository.save(feedback);
            return parse(feedback);
        }else {
            throw new BadRequestException("You can only update feedbacks that belong to you.");
        }
    }

    private FeedbackResponseDto parse(FeedbackEntity dto){
        FeedbackResponseDto map = modelMapper.map(dto, FeedbackResponseDto.class);
        map.setId(dto.getId());
        map.setUserName(dto.getUser().getFirstName());
        return map;
    }

    private FeedbackEntity parse(FeedBackCreateDto dto, UUID userId){
        ProductEntity productId = productService.findById(dto.getProductId());
        UserEntity user = userService.findById(userId);
        return new FeedbackEntity(productId, user, dto.getRate(), dto.getText());
    }

}
