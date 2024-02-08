package uz.cosinus.thinkstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.thinkstore.entity.ProductFieldValues;

import java.util.List;
import java.util.UUID;

public interface ProductFieldValuesRepository extends JpaRepository<ProductFieldValues, UUID> {
    Page<ProductFieldValues> findAllByIsActiveTrue(Pageable pageable);
    List<ProductFieldValues> findAllByProductFieldsId(UUID productFields_id);
}
