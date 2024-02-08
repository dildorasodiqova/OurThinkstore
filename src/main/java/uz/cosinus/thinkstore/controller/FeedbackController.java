package uz.cosinus.thinkstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.createDto.FeedBackCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.FeedbackResponseDto;
import uz.cosinus.thinkstore.service.feedbackService.FeedbackService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    @PostMapping("/create")
    public ResponseEntity<FeedbackResponseDto> create(@RequestBody FeedBackCreateDto createDTO) {
        return ResponseEntity.ok(feedbackService.create(createDTO));
    }

    @GetMapping("/getById/{feedbackId}")
    public ResponseEntity<FeedbackResponseDto> getById(@PathVariable UUID feedbackId){
        return ResponseEntity.ok(feedbackService.findById(feedbackId));
    }

    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<String> delete(@PathVariable UUID feedbackId, Principal principal) {
        return ResponseEntity.ok(feedbackService.delete(feedbackId, UUID.fromString(principal.getName())));
    }

    @GetMapping("/feedbacksOfProduct/{productId}")
    public ResponseEntity<List<FeedbackResponseDto>> feedbacksOfProduct(@PathVariable UUID productId){
        return ResponseEntity.ok(feedbackService.feedbacksOfProduct(productId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/update/{feedbackId}")
    public ResponseEntity<FeedbackResponseDto> update(@PathVariable UUID feedbackId,@RequestParam String text){
        return ResponseEntity.ok(feedbackService.update(feedbackId, text));
    }

}
