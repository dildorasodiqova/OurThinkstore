package uz.cosinus.thinkstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.cosinus.thinkstore.entity.TransactionEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    Page<TransactionEntity> findAllByIsActiveTrue(Pageable pageable);
    @Query("SELECT t FROM transaction t WHERE t.order.user.id = :userId")
    List<TransactionEntity> transactionsOfUser(@Param("userId") UUID userId);
}
