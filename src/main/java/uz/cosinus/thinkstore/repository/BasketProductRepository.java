package uz.cosinus.thinkstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.cosinus.thinkstore.entity.BasketProductEntity;

import java.util.List;
import java.util.UUID;

public interface BasketProductRepository  extends JpaRepository<BasketProductEntity, UUID> {
    BasketProductEntity getById(UUID basketId);
    Page<BasketProductEntity> findAllByIsActiveTrue(PageRequest pageRequest);
    List<BasketProductEntity> findAllByIsActiveTrueAndUserId(UUID userId);


    @Modifying
    @Transactional
    @Query("UPDATE basketProduct bp " +
            "SET bp.count = bp.count + :count " +
            "WHERE bp.user.id = :userId AND bp.product.id = :productId")
    BasketProductEntity updateCount(@Param("userId") UUID userId, @Param("productId") UUID productId, @Param("count") int count);
}
