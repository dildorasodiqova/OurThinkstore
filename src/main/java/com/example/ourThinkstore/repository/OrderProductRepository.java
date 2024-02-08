package com.example.ourThinkstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.cosinus.thinkstore.entity.OrderProductEntity;

import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {
}
