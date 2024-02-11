package uz.cosinus.thinkstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.cosinus.thinkstore.entity.ProductLikeEntity;

import java.util.UUID;

public interface ProductLikeRepository extends JpaRepository<ProductLikeEntity, UUID> {

    boolean existsAllByProductIdAndUserIdAndIsActiveTrue(UUID product_id, UUID user_id);
    @Modifying
    @Transactional
    @Query("UPDATE productLike pl " +
            "SET pl.isActive = false " +
            "WHERE pl.product.id = :productId AND pl.user.id = :userId")
    void softDeleteByProductIdAndUserId(UUID productId, UUID userId);
    Long countAllByProductIdAndIsActiveTrue(UUID product_id);
}
