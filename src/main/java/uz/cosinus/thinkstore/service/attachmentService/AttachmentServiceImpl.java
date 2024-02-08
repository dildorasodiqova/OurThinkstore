package uz.cosinus.thinkstore.service.attachmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.cosinus.thinkstore.dto.responseDto.ApiResponse;
import uz.cosinus.thinkstore.dto.responseDto.AttachResDTO;
import uz.cosinus.thinkstore.entity.AttachmentEntity;
import uz.cosinus.thinkstore.exception.ItemNotFoundException;
import uz.cosinus.thinkstore.repository.AttachmentRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static uz.cosinus.thinkstore.dto.responseDto.AttachResDTO.toDTO;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    @Value("${attach.upload.folder}") //
    private String folderName;

    @Value("${server.url}")
    private String attachUrl;

    private final AttachmentRepository attachmentRepository;

    @Override
    public AttachResDTO upload(MultipartFile file, UUID userId) {
        if (file.isEmpty()) {
            log.warn("Attach error : file not found");
            throw new ItemNotFoundException("File not found");
        }

        String pathFolder = getYmDString();
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            boolean t = folder.mkdirs(); /// shu nomli file bolmasa yaratilyapti
        }

        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            AttachmentEntity entity = new AttachmentEntity();
            entity.setCreatedId(userId);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setIsActive(true);

            attachmentRepository.save(entity);

            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + entity.getId() + "." + extension);
            Files.write(path, bytes);

            return toDTO(entity.getId().toString(), getUrl(entity.getId().toString()));
        } catch (IOException e) {
            log.warn("Attach error : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    @Override
    public byte[] open_general(UUID fileName) {
        AttachmentEntity entity = findById(fileName);
        try {
            BufferedImage originalImage = ImageIO.read(new File(getPath(entity)));
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.getExtension(), boas);
            boas.flush();
            byte[] imageInByte = boas.toByteArray();
            boas.close();
            return imageInByte;
        } catch (Exception e) {
            log.warn("Attach error : {}", e.getMessage());
            return new byte[0];
        }
    }

    @Override
    public ResponseEntity<Resource> download(UUID fileName) {
        AttachmentEntity entity = findById(fileName);
        try {
            Path file = Paths.get(getPath(entity));
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + entity.getOrigenName() + "\"").body(resource);
            } else {
                log.warn("Attach error : Could not read the file!");
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.warn("Attach error : {}", e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public AttachmentEntity findById(UUID id) {
        Optional<AttachmentEntity> optional = attachmentRepository.findByIdAndIsActiveTrue(id);
        if (optional.isEmpty()) {
            log.warn("Attach error : file not found");
            throw new ItemNotFoundException("File not found");
        }
        return optional.get();
    }

    private String getPath(AttachmentEntity entity) {
        return folderName + "/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension();
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    @Override
    public String getUrl(String fileName) {
        return attachUrl + "/api/v1/attach/open/" + fileName;
    }

    @Override
    public ApiResponse<?> delete(UUID id) {
        AttachmentEntity entity = findById(id);
        entity.setIsActive(false);

        attachmentRepository.save(entity);
        return ApiResponse.success();
    }

}
