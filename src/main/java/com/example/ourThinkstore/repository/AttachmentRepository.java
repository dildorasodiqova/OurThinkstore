package com.example.ourThinkstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.thinkstore.entity.AttachmentEntity;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, String> {

    Optional<AttachmentEntity> findByIdAndVisibleIsTrue(String id);
}