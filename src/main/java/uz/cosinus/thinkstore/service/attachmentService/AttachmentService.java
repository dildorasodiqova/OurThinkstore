package uz.cosinus.thinkstore.service.attachmentService;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.cosinus.thinkstore.dto.responseDto.ApiResponse;
import uz.cosinus.thinkstore.dto.responseDto.AttachResDTO;
import uz.cosinus.thinkstore.entity.AttachmentEntity;

import java.util.UUID;

public interface AttachmentService {
    AttachResDTO upload(MultipartFile file, UUID userId);
    byte[] open_general(UUID fileName);
    ResponseEntity<Resource> download(UUID fileName);
    AttachmentEntity findById(UUID id);
    String getUrl(UUID fileName);
    ApiResponse<?> delete(UUID id);

}
