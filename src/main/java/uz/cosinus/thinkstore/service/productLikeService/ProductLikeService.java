package uz.cosinus.thinkstore.service.productLikeService;

import uz.cosinus.thinkstore.dto.createDto.ProductLikeCreateDto;

import java.util.UUID;

public interface ProductLikeService {
    String create (ProductLikeCreateDto dto, UUID currentUser);
    Long getALlLike(UUID productId);
}
