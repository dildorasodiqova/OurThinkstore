package uz.cosinus.thinkstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.thinkstore.entity.ProductFields;

import java.util.UUID;

public interface ProductFieldsRepository extends JpaRepository<ProductFields, UUID> {
    Page<ProductFields> findAllByIsActiveTrue(Pageable pageable);
    boolean existsAllByProductIdAndName(UUID product_id, String name);
}
