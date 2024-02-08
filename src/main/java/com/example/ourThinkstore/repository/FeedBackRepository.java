package com.example.ourThinkstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.thinkstore.entity.FeedbackEntity;

import java.util.List;
import java.util.UUID;

public interface FeedBackRepository extends JpaRepository<FeedbackEntity, UUID> {
    List<FeedbackEntity> findAllByProductId(UUID product_id);
}
