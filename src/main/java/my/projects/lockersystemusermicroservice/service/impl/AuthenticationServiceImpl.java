package my.projects.lockersystemusermicroservice.service.impl;

import my.projects.lockersystemusermicroservice.dto.RegisterUserDTO;
import my.projects.lockersystemusermicroservice.entity.User;
import my.projects.lockersystemusermicroservice.enums.Role;
import my.projects.lockersystemusermicroservice.repository.UserRepository;
import my.projects.lockersystemusermicroservice.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                Role.valueOf(registerUserDTO.getRole()));

        this.userRepository.save(user);
        return true;
    }
}
