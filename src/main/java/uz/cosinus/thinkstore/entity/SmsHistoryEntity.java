package uz.cosinus.thinkstore.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.cosinus.thinkstore.enums.SmsStatus;
import uz.cosinus.thinkstore.enums.SmsType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "sms_history")
@Table(name = "sms_history")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sms_code")
    private String smsCode;

    @Column(name = "phone")
    private String phone;

    private UUID clientLogin;
    @Column(nullable = false)
    private String message;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SmsType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SmsStatus status;

    @Column(name = "sms_count")
    private Integer smsCount;

    @Column(name = "used_time")
    private LocalDateTime usedTime;

    @Column(name = "is_send")
    private Boolean isSend = false;

    private String providerStatus;
    private String messageId;
    private LocalDateTime providerStatusDate;
    private LocalDateTime createdDate;
}
