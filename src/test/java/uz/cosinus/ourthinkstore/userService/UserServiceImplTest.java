package uz.cosinus.ourthinkstore.userService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.cosinus.thinkstore.dto.createDto.UserCreateDto;
import uz.cosinus.thinkstore.dto.responseDto.ApiResponse;
import uz.cosinus.thinkstore.dto.responseDto.UserResponseDto;
import uz.cosinus.thinkstore.entity.UserEntity;
import uz.cosinus.thinkstore.enums.SmsType;
import uz.cosinus.thinkstore.repository.UserRepository;
import uz.cosinus.thinkstore.service.SmsApiService;
import uz.cosinus.thinkstore.service.userService.UserServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SmsApiService smsApiService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void signUpTest() {
        UserEntity user = mock(UserEntity.class);
        UserResponseDto responseDto = mock(UserResponseDto.class);
        UserCreateDto userCreateDto = mock(UserCreateDto.class);
        doReturn("test").when(userCreateDto).getPhoneNumber();
        doReturn(Optional.empty()).when(userRepository).findByPhoneNumber(anyString());
        doReturn(user).when(modelMapper).map(any(), any());
        doReturn("test").when(user).getPassword();
        doReturn("test").when(user).getPhoneNumber();
        doReturn(UUID.randomUUID()).when(user).getId();
        doReturn(new Timestamp(System.currentTimeMillis())).when(user).getCreatedDate();
        doReturn("test").when(passwordEncoder).encode(any());
        doReturn(user).when(userRepository).save(any(UserEntity.class));
        doReturn(ApiResponse.success()).when(smsApiService).sendMessage(anyString(), any(SmsType.class), anyString(), any(UUID.class));
        doReturn(responseDto).when(modelMapper).map(user, UserResponseDto.class);
        UserResponseDto response = userService.signUp(userCreateDto);
        assertSame(responseDto, response);
        verify(userRepository, times(1)).findByPhoneNumber(anyString());
        verify(modelMapper, times(2)).map(any(), any());
        verify(passwordEncoder, times(1)).encode(any());
        verify(smsApiService, times(1)).sendMessage(anyString(), any(SmsType.class), anyString(), any(UUID.class));
    }
}