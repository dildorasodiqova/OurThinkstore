package com.example.ourThinkstore.dto.createDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    @NotBlank(message = "First name cannot be empty !")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty !")
    private String lastName;

    @NotBlank(message = "Password cannot be empty !")
    private String password;

    @NotBlank(message = "Phone number cannot be empty !")
    private String phoneNumber;
}
