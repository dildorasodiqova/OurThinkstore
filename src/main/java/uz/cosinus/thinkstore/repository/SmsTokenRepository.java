package uz.cosinus.thinkstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.cosinus.thinkstore.entity.SmsTokenEntity;


import java.util.Optional;


public interface SmsTokenRepository extends JpaRepository<SmsTokenEntity,String> {
    Optional<SmsTokenEntity> findByEmail(String email);
}
