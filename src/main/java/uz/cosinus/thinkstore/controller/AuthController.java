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

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetDto forgetDto) {
        return ResponseEntity.ok(userService.forgetPassword(forgetDto));
    }

    @PermitAll
    @PostMapping("/get-verification-code")
    public String sendVerifyCode(@RequestBody String email) {
        return userService.getVerificationCode(email);
    }

    @Operation(
            description = "This API is used for verifying",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PermitAll
    @PostMapping("/verify")
    public UserResponseDto verify(@RequestBody VerifyDto verifyDto) {
        return userService.verify(verifyDto);
    }

    @PermitAll
    @GetMapping("/verify-token")
    public SubjectDto verifyToken(@RequestBody String token) {
        return userService.verifyToken(token);
    }

}
