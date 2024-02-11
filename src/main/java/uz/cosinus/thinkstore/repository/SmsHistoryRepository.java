package uz.cosinus.thinkstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.cosinus.thinkstore.entity.SmsHistoryEntity;
import uz.cosinus.thinkstore.enums.SmsStatus;

import java.util.Optional;


public interface SmsHistoryRepository extends JpaRepository<SmsHistoryEntity, Long> {

    @Transactional
    @Modifying
    @Query("""
            update sms_history set messageId = ?2 where id = ?1
            """)
    void updateMessageId(Long id, String body);

    Optional<SmsHistoryEntity> findByMessageId(String messageId);

    Optional<SmsHistoryEntity> findTop1ByPhoneAndStatusOrderByCreatedDateDesc(String phone, SmsStatus status);

}