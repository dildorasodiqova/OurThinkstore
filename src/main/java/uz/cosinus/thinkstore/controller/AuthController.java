package uz.cosinus.thinkstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.createDto.*;
import uz.cosinus.thinkstore.dto.responseDto.JwtResponse;
import uz.cosinus.thinkstore.dto.responseDto.UserResponseDto;
import uz.cosinus.thinkstore.service.SmsApiService;
import uz.cosinus.thinkstore.service.userService.UserService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Auth Management")
@SecurityRequirement(name = "Bearer Authentication")
public class AuthController {
    private final UserService userService;
    private final SmsApiService smsApiService;

    @PermitAll
    @PostMapping("/sign-up")
    public UserResponseDto signUp(@Valid @RequestBody UserCreateDto dto) {
        return userService.signUp(dto);
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody VerifyDtoP verifyDtoP) {
        return userService.signIn(verifyDtoP);
    }

    @PostMapping("/access-token")
    public String getAccessToken(@RequestBody String refreshToken, Principal principal) {
        return userService.getAccessToken(refreshToken, UUID.fromString(principal.getName()));
    }

     @PermitAll
    @PostMapping("/verify-code")
    public UserResponseDto verifyCode(@RequestBody VerifyDto code) {
        return smsApiService.verifyCode(code);
    }

}
