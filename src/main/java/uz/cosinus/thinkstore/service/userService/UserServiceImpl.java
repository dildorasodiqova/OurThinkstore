package uz.cosinus.thinkstore.service.userService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.cosinus.thinkstore.dto.createDto.*;
import uz.cosinus.thinkstore.dto.responseDto.JwtResponse;
import uz.cosinus.thinkstore.dto.responseDto.UserResponseDto;
import uz.cosinus.thinkstore.entity.UserEntity;
import uz.cosinus.thinkstore.exception.DataAlreadyExistsException;
import uz.cosinus.thinkstore.exception.DataNotFoundException;
import uz.cosinus.thinkstore.repository.UserRepository;
import uz.cosinus.thinkstore.service.jwt.JwtService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public UserResponseDto signUp(UserCreateDto dto) {
        Optional<UserEntity> optionalUser = userRepository.findByPhoneNumber(dto.getPhoneNumber());
        if(optionalUser.isPresent()) {
            if(optionalUser.get().getIsAuthenticated().equals(false)){
                throw new AuthenticationCredentialsNotFoundException("User already exists but not verified");
            }
            else throw new DataAlreadyExistsException("User already exists with phone number: " + dto.getPhoneNumber());
        }

        UserEntity user = modelMapper.map(dto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return parse(user);
    }




    @Override
    public JwtResponse signIn(VerifyDtoP verifyDtoP) {
        UserEntity userEntity = userRepository.findByPhoneNumber(verifyDtoP.getPhoneNumber())
                .orElseThrow(() -> new DataNotFoundException("User not found with email: " + verifyDtoP.getPhoneNumber()));
        if(userEntity.getIsAuthenticated() && userEntity.getIsActive()) {
            if(passwordEncoder.matches(verifyDtoP.getPassword(), userEntity.getPassword())) {
                return new JwtResponse(jwtService.generateAccessToken(userEntity), jwtService.generateRefreshToken(userEntity));
            }
            throw new AuthenticationCredentialsNotFoundException("Password didn't match");
        }
        throw new AuthenticationCredentialsNotFoundException("Not verified");
    }


    @Override
    public SubjectDto verifyToken(String token) {
        try{
            Jws<Claims> claimsJws = jwtService.extractToken(token);
            Claims claims = claimsJws.getBody();
            String subject = claims.getSubject();
            List<String> roles = (List<String>) claims.get("roles");
            return new SubjectDto(UUID.fromString(subject),roles);
        }catch (ExpiredJwtException e){
            throw new AuthenticationCredentialsNotFoundException("Token expired");
        }

    }

    @Override
    public List<UserResponseDto> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserEntity> users = userRepository.findAllByIsActiveTrue(pageRequest);
        List<UserEntity> content = users.getContent();
        return parse(content);
    }


    @Override
    public String getAccessToken(String refreshToken, UUID userId) {
        try{

            Jws<Claims> claimsJws = jwtService.extractToken(refreshToken);
            Claims claims = claimsJws.getBody();
            String subject = claims.getSubject();

            UserEntity userEntity = userRepository.findById(UUID.fromString(subject))
                    .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));
            return jwtService.generateAccessToken(userEntity);

        }catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Token expired");
        }
    }


    @Override
    public String deleteUser(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        userEntity.setIsActive(false);
        userRepository.save(userEntity);
        return "User deleted";
    }

    @Override
    public UserEntity findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("User not found"));
    }

    @Override
    public UserResponseDto getById(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return parse(user);

    }

    private UserResponseDto parse(UserEntity userEntity) {
        UserResponseDto map = modelMapper.map(userEntity, UserResponseDto.class);
        map.setId(userEntity.getId());
        map.setCreatedDate(userEntity.getCreatedDate());
        return map;

    }

    private List<UserResponseDto> parse(List<UserEntity> userEntities) {
        List<UserResponseDto> list = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            UserResponseDto map = modelMapper.map(userEntity, UserResponseDto.class);
            map.setId(userEntity.getId());
            map.setCreatedDate(userEntity.getCreatedDate());
            list.add(map);
        }
        return list;
    }
}
