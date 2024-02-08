package com.example.ourThinkstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.cosinus.thinkstore.dto.responseDto.UserResponseDto;
import uz.cosinus.thinkstore.service.userService.UserService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/getByid")
    public UserResponseDto getById(Principal principal) {
        return userService.getById(UUID.fromString(principal.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public List<UserResponseDto> getAll(@RequestParam(value = "page", defaultValue = "0")
                                        int page,
                                        @RequestParam(value = "size", defaultValue = "5")
                                        int size) {
        return userService.getAll(page, size);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }
}

