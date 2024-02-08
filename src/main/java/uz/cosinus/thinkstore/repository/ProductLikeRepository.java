package uz.cosinus.thinkstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.thinkstore.entity.ProductLikeEntity;

import java.util.UUID;

public interface ProductLikeRepository extends JpaRepository<ProductLikeEntity, UUID> {

    boolean existsAllByProductIdAndUserId(UUID product_id, UUID user_id);
    void deleteAllByProductIdAndUserId(UUID product_id, UUID user_id);
    Long countAllByProductId(UUID product_id);
}
