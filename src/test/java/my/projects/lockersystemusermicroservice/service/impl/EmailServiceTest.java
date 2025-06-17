package my.projects.lockersystemusermicroservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private final String recipientEmail = "test@example.com";
    private final String accessCode = "123ABC";
    private final String location = "Locker A";
    private final LocalDateTime expiresAt = LocalDateTime.of(2025, 5, 30, 14, 0);

    @Test
    void sendAccessCodeEmail_shouldSendEmailSuccessfully() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.sendAccessCodeEmail(recipientEmail, accessCode, location, expiresAt);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendAccessCodeEmail_shouldHandleExceptionWhenSendingFails() {
        doThrow(new MailSendException("SMTP error")).when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() ->
                emailService.sendAccessCodeEmail(recipientEmail, accessCode, location, expiresAt)
        );

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendPackageReceivedEmail_shouldSendEmailSuccessfully() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.sendPackageReceivedEmail(recipientEmail, location);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendPackageReceivedEmail_shouldHandleExceptionWhenSendingFails() {
        doThrow(new MailSendException("SMTP error")).when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() ->
                emailService.sendPackageReceivedEmail(recipientEmail, location)
        );

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendAccessCodeEmail_shouldFormatEmailContentCorrectly() {
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendAccessCodeEmail(recipientEmail, accessCode, location, expiresAt);

        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage message = messageCaptor.getValue();

        assertEquals("Your Package Access Code", message.getSubject());
        assertArrayEquals(new String[]{recipientEmail}, message.getTo());

        String body = message.getText();
        assertTrue(body.contains(location));
        assertTrue(body.contains(accessCode));
        assertTrue(body.contains(expiresAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @Test
    void sendPackageReceivedEmail_shouldFormatEmailContentCorrectly() {
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendPackageReceivedEmail(recipientEmail, location);

        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage message = messageCaptor.getValue();

        assertEquals("Your Package Is Picked Up", message.getSubject());
        assertArrayEquals(new String[]{recipientEmail}, message.getTo());

        String body = message.getText();
        assertTrue(body.contains(location));
        assertTrue(body.contains("picked up your package"));
    }

}
