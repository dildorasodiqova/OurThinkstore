package uz.cosinus.thinkstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.cosinus.thinkstore.dto.responseDto.ApiResponse;
import uz.cosinus.thinkstore.dto.responseDto.AttachResDTO;
import uz.cosinus.thinkstore.service.attachmentService.AttachmentService;

import java.security.Principal;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Attach")
@RequestMapping("/api/v1/attachment")
public class AttachController {
    private final AttachmentService attachmentService;


//    @Operation(
//            summary = "Small summary of the end-point",
//            description = "A detailed description of the end-point",
//            parameters = {
//                    @Parameter(
//                            description = "Some text",
//                            content = @Content(
//                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE
//                            ),
//                            name = "file"
//                    )
//            }
//    )

    @PostMapping(value = {"/upload"}, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<AttachResDTO> create(@RequestParam("file") MultipartFile file, Principal principal) {
        log.info("upload attach  = {}", file.getOriginalFilename()); // console un
        return ResponseEntity.ok(attachmentService.upload(file, UUID.fromString(principal.getName())));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable("id") UUID photoId) {
        log.info("Delete attach  = {}", photoId);
        return ResponseEntity.ok(attachmentService.delete(photoId));
    }


    @PermitAll
    @GetMapping(value = "/open/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open_general(@PathVariable("id") UUID id) {
        log.info("open attach  ={}", id);
        return attachmentService.open_general(id);
    }


    @PermitAll
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") UUID fileName) {
        log.info("download attach  ={}", fileName);
        return attachmentService.download(fileName);
    }
}
