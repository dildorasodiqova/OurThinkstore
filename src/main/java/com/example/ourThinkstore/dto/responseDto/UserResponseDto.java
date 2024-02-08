package com.example.ourThinkstore.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private LocalDateTime createdDate;
}
