package my.projects.lockersystemusermicroservice.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAccessCodeEmail(String recipientEmail, String accessCode, String location, LocalDateTime expriresAt) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Package Access Code");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedExpiration = expriresAt.format(formatter);

        String emailBody = String.format("""
                Dear Customer,

                You have received a new package at: %s.
                Your access code for the package is: %s
                and expires on: %s

                Your SmartLockerHub Team""", location, accessCode, formattedExpiration);

        message.setText(emailBody);

        try {
            this.mailSender.send(message);
            System.out.println("Email sent to " + recipientEmail);

        } catch (Exception e) {
            System.err.println("Failed to send email to " + recipientEmail);
            e.printStackTrace();
        }
    }

    public void sendPackageReceivedEmail(String recipientEmail, String location) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Package Is Picked Up");

        String emailBody = String.format("""
                Dear Customer,

                You have picked up your package at: %s.
                
                Thank you for using our services!

                Your SmartLockerHub Team""", location);

        message.setText(emailBody);

        try {
            this.mailSender.send(message);
            System.out.println("Email sent to " + recipientEmail);

        } catch (Exception e) {
            System.err.println("Failed to send email to " + recipientEmail);
            e.printStackTrace();
        }
    }
}
