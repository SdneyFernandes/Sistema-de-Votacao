package br.com.voting_system_user_service.serviceTest.AuthTest;

import br.com.voting_system_user_service.dto.LoginRequest;
import br.com.voting_system_user_service.dto.RegisterRequest;
import br.com.voting_system_user_service.entity.User;
import br.com.voting_system_user_service.enums.Role;
import br.com.voting_system_user_service.repository.UserRepository;
import br.com.voting_system_user_service.service.AuthService;
import br.com.voting_system_user_service.service.UserService;
import br.com.voting_system_user_service.dto.UserDTO;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * @author fsdney
 */


public class UserAuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private br.com.voting_system_user_service.security.JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(jwtUtil, passwordEncoder, userRepository, meterRegistry);
        userService = new UserService(userRepository, meterRegistry);
    }

    @Test
    void testRegisterUser_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setUserName("fulano");
        request.setEmail("fulano@email.com");
        request.setPassword("123456");
        request.setRole(Role.USER);

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded123");

        String result = authService.registerUser(request);

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("Usuário registrado com sucesso!", result);
    }

    @Test
    void testLoginUser_Success() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedpass");
        user.setRole(Role.USER);
        user.setId(1L);

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(1L, Role.USER)).thenReturn("token123");

        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("123456");

        String token = authService.loginUser(request);

        assertEquals("token123", token);
    }

    @Test
    void testGetUserById_Found() {
        User user = new User();
        user.setId(1L);
        user.setUserName("user1");
        user.setEmail("user@email.com");
        user.setRole(Role.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUserName());
    }

    @Test
    void testGetAllUsers() {
        User user = new User();
        user.setUserName("UserX");
        user.setEmail("x@email.com");
        user.setRole(Role.USER);

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> result = userService.getAllUsers();

        assertFalse(result.isEmpty());
        assertEquals("UserX", result.get(0).getUserName());
    }
}
