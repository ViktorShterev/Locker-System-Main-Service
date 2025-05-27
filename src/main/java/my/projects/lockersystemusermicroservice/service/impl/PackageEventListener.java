package my.projects.lockersystemusermicroservice.service.impl;

import my.projects.lockersystemusermicroservice.dto.kafka.AccessCodeEventDTO;
import my.projects.lockersystemusermicroservice.entity.AccessCode;
import my.projects.lockersystemusermicroservice.entity.User;
import my.projects.lockersystemusermicroservice.repository.AccessCodeRepository;
import my.projects.lockersystemusermicroservice.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PackageEventListener {

    private final AccessCodeRepository accessCodeRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public PackageEventListener(AccessCodeRepository accessCodeRepository, UserRepository userRepository, EmailService emailService) {
        this.accessCodeRepository = accessCodeRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "package-placed-access-code", groupId = "user-service-group")
    public void sendingPackageEmail(AccessCodeEventDTO event) {
        System.out.println("Received event: " + event);

        Optional<User> user = this.userRepository.findByEmail(event.getRecipientEmail());

        if (user.isPresent()) {
            AccessCode accessCode = new AccessCode();
            accessCode.setCode(event.getAccessCode());
            accessCode.setPackageId(event.getPackageId());
            accessCode.setUsed(false);
            accessCode.setUser(user.get());
            accessCode.setExpiresAt(LocalDateTime.now().plusDays(3));

            this.accessCodeRepository.save(accessCode);

            this.emailService.sendAccessCodeEmail(event.getRecipientEmail(), event.getAccessCode(), event.getLocation(), accessCode.getExpiresAt());
        }
    }

    @KafkaListener(topics = "package-received-message", groupId = "user-service-group")
    public void sendingPackageReceivedEmail(AccessCodeEventDTO event) {
        System.out.println("Received event: " + event);

        Optional<User> user = this.userRepository.findByEmail(event.getRecipientEmail());
        Optional<AccessCode> accessCode = this.accessCodeRepository.findByCode(event.getAccessCode());

        if (user.isPresent() && accessCode.isPresent()) {
            AccessCode code = accessCode.get();

            code.setUsed(true);
            this.accessCodeRepository.save(code);

            this.emailService.sendPackageReceivedEmail(event.getRecipientEmail(), event.getLocation());
        }
    }
}
