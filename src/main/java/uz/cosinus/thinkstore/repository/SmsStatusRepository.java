package uz.cosinus.thinkstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.thinkstore.entity.SmsStatusEntity;


public interface SmsStatusRepository extends JpaRepository<SmsStatusEntity, Long> {
}