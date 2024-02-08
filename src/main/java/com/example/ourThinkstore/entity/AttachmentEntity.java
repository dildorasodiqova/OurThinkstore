package com.example.ourThinkstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Table(name = "attach")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentEntity extends BaseEntity {
    @Column(name = "created_id")
    private UUID createdId; /// kim create qilgan bolsa osha insonni id si

    @Column(name = "path")
    private String path;

    @Column(name = "extension")
    private String extension; /// jpg, png , mp3

    @Column(name = "origen_name")
    private String origenName;   /// bir hil nomli rasm saqlamaslik un bunga uuid beriladi.

    @Column(name = "size")
    private Long size;          /// file size =  1 mb yoki 5 mb degan narsa, lekin bu kb da boladi
}