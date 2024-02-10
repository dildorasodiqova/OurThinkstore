package uz.cosinus.thinkstore.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    protected UUID id;

    @CreationTimestamp
    protected Timestamp createdDate;

    @UpdateTimestamp
    protected Timestamp updatedDate;

    protected Boolean isActive = true;
}
