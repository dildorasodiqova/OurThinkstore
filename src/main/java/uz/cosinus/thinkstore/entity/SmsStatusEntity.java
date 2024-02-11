package uz.cosinus.thinkstore.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long smsId;

    private String messageId;

    private String status;

    private LocalDateTime statusDate;

    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdDate;
}
