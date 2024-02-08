package uz.cosinus.thinkstore.service.attachmentService;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.cosinus.thinkstore.dto.responseDto.ApiResponse;
import uz.cosinus.thinkstore.dto.responseDto.AttachResDTO;

import java.util.UUID;

public interface AttachmentService {
    AttachResDTO upload(MultipartFile file, UUID userId);
    byte[] open_general(String fileName);
    ResponseEntity<Resource> download(String fileName);
    AttachmentEntity getEntity(String id);
    String getUrl(String fileName);
    ApiResponse<?> delete(String id);
}
