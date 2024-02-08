package uz.cosinus.thinkstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.thinkstore.entity.AttachmentEntity;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, String> {

    Optional<AttachmentEntity> findByIdAndIsActiveTrue(UUID id);
}