package uz.cosinus.thinkstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import uz.cosinus.thinkstore.entity.ProductLikeEntity;

import java.util.UUID;

public interface ProductLikeRepository extends JpaRepository<ProductLikeEntity, UUID> {

    boolean existsAllByProductIdAndUserId(UUID product_id, UUID user_id);
    @Modifying
    @Transactional
    void deleteAllByProductIdAndUserId(UUID product_id, UUID user_id);
    Long countAllByProductId(UUID product_id);
}
