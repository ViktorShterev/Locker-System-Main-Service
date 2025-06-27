package my.projects.lockersystemusermicroservice.service.impl;

import my.projects.lockersystemusermicroservice.dto.kafka.AccessCodeEventDTO;
import my.projects.lockersystemusermicroservice.entity.AccessCode;
import my.projects.lockersystemusermicroservice.entity.User;
import my.projects.lockersystemusermicroservice.repository.AccessCodeRepository;
import my.projects.lockersystemusermicroservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageEventListenerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccessCodeRepository accessCodeRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PackageEventListener listener;

    private AccessCodeEventDTO event;

    @BeforeEach
    void setUp() {
        event = new AccessCodeEventDTO("CODE123", 1L, "test@example.com", "Location1");
    }

    @Test
    void testSendingPackageEmail_userExists() {
        User user = new User();
        when(userRepository.findByEmail(event.getRecipientEmail())).thenReturn(Optional.of(user));
        when(accessCodeRepository.save(any(AccessCode.class))).thenAnswer(inv -> inv.getArgument(0));

        listener.sendingPackageEmail(event);

        verify(accessCodeRepository).save(any(AccessCode.class));
        verify(emailService).sendAccessCodeEmail(eq("test@example.com"), eq("CODE123"), eq("Location1"), any(LocalDateTime.class));
    }

    @Test
    void sendingPackageEmail_userNotFound_shouldDoNothing() {
        when(userRepository.findByEmail(event.getRecipientEmail())).thenReturn(Optional.empty());

        listener.sendingPackageEmail(event);

        verify(accessCodeRepository, never()).save(any());
        verify(emailService, never()).sendAccessCodeEmail(any(), any(), any(), any());
    }

    @Test
    void sendingPackageReceivedEmail_success_shouldMarkCodeUsedAndSendEmail() {
        User user = new User();
        AccessCode code = new AccessCode();
        code.setUsed(false);

        when(userRepository.findByEmail(event.getRecipientEmail())).thenReturn(Optional.of(user));
        when(accessCodeRepository.findByCode(event.getAccessCode())).thenReturn(Optional.of(code));
        when(accessCodeRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        listener.sendingPackageReceivedEmail(event);

        assertTrue(code.isUsed());
        verify(accessCodeRepository).save(code);
        verify(emailService).sendPackageReceivedEmail("test@example.com", "Location1");
    }

    @Test
    void sendingPackageReceivedEmail_accessCodeNotFound_shouldDoNothing() {
        when(userRepository.findByEmail(event.getRecipientEmail())).thenReturn(Optional.of(new User()));
        when(accessCodeRepository.findByCode(event.getAccessCode())).thenReturn(Optional.empty());

        listener.sendingPackageReceivedEmail(event);

        verify(accessCodeRepository, never()).save(any());
        verify(emailService, never()).sendPackageReceivedEmail(any(), any());
    }

    @Test
    void sendingPackageReceivedEmail_userNotFound_shouldDoNothing() {
        when(userRepository.findByEmail(event.getRecipientEmail())).thenReturn(Optional.empty());
        when(accessCodeRepository.findByCode(event.getAccessCode())).thenReturn(Optional.of(new AccessCode()));

        listener.sendingPackageReceivedEmail(event);
        
        verify(accessCodeRepository, never()).save(any());
        verify(emailService, never()).sendPackageReceivedEmail(any(), any());
    }
}
