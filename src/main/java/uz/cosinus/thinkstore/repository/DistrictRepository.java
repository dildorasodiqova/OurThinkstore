package uz.cosinus.thinkstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DistrictRepository extends JpaRepository<DistrictEntity, UUID> {
    Page<DistrictEntity> findAllByIsActiveTrue(PageRequest pageRequest);
}
