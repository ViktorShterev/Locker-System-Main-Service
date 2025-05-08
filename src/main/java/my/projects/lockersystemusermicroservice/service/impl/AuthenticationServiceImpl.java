package my.projects.lockersystemusermicroservice.service.impl;

import my.projects.lockersystemusermicroservice.dto.RegisterUserDTO;
import my.projects.lockersystemusermicroservice.entity.User;
import my.projects.lockersystemusermicroservice.entity.UserDetails;
import my.projects.lockersystemusermicroservice.enums.Role;
import my.projects.lockersystemusermicroservice.repository.UserRepository;
import my.projects.lockersystemusermicroservice.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(RegisterUserDTO registerUserDTO) {

        if (registerUserDTO == null) {
            return false;
        }

        User user = new User(registerUserDTO.getFullName(),
                registerUserDTO.getEmail(),
                this.passwordEncoder.encode(registerUserDTO.getPassword()),
                Role.valueOf(registerUserDTO.getRole()),
                UUID.randomUUID());

        this.userRepository.save(user);
        return true;
    }

    @Override
    public Optional<UserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof UserDetails userDetails) {
            return Optional.of(userDetails);
        }
        return Optional.empty();
    }
}
