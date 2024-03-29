package uz.cosinus.thinkstore.service.userService;

import uz.cosinus.thinkstore.dto.createDto.*;
import uz.cosinus.thinkstore.dto.responseDto.JwtResponse;
import uz.cosinus.thinkstore.dto.responseDto.UserResponseDto;
import uz.cosinus.thinkstore.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserEntity findById(UUID userId);

    String deleteUser(UUID userId);
    UserResponseDto signUp(UserCreateDto dto);

    UserResponseDto getById(UUID userId);
    List<UserResponseDto> getAll(Integer page, Integer size);
    JwtResponse getAccessToken(String refreshToken);
    JwtResponse signIn(VerifyDtoP verifyDtoP);

}
