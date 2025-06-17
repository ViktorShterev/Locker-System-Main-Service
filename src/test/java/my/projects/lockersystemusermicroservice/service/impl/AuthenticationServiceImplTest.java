package my.projects.lockersystemusermicroservice.service.impl;

import my.projects.lockersystemusermicroservice.dto.RegisterUserDTO;
import my.projects.lockersystemusermicroservice.entity.User;
import my.projects.lockersystemusermicroservice.entity.UserDetails;
import my.projects.lockersystemusermicroservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private RegisterUserDTO registerUserDTO;

    @BeforeEach
    void setup() {
        registerUserDTO = new RegisterUserDTO(
                "Test Name",
                "test@example.com",
                "password123",
                "CUSTOMER");
    }

    @Test
    void register_ShouldReturnTrue_WhenValidDTO() {
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        boolean result = authenticationService.register(registerUserDTO);

        assertTrue(result);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ShouldReturnFalse_WhenDTOIsNull() {
        boolean result = authenticationService.register(null);

        assertFalse(result);
        verifyNoInteractions(userRepository, passwordEncoder);
    }

    @Test
    void getCurrentUser_ShouldReturnUserDetails_WhenAuthenticated() {
        UserDetails mockUserDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(mockUserDetails);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);

        Optional<UserDetails> result = authenticationService.getCurrentUser();

        assertTrue(result.isPresent());
        assertEquals(mockUserDetails, result.get());
    }

    @Test
    void getCurrentUser_ShouldReturnEmpty_WhenAuthenticationIsNull() {
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(context);

        Optional<UserDetails> result = authenticationService.getCurrentUser();

        assertTrue(result.isEmpty());
    }

    @Test
    void getCurrentUser_ShouldReturnEmpty_WhenPrincipalNotUserDetails() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("not user details");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        Optional<UserDetails> result = authenticationService.getCurrentUser();

        assertTrue(result.isEmpty());
    }
}
