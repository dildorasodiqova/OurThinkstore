package uz.cosinus.thinkstore.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttachResDTO {
    private String id;
    private String url;

    public static AttachResDTO toDTO(String id, String url) {
        return new AttachResDTO(id, url);
    }
}
