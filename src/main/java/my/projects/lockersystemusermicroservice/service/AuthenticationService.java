package my.projects.lockersystemusermicroservice.service;

import my.projects.lockersystemusermicroservice.dto.RegisterUserDTO;
import my.projects.lockersystemusermicroservice.entity.UserDetails;

import java.util.Optional;

public interface AuthenticationService {

    boolean register(RegisterUserDTO registerUserDTO);

    Optional<UserDetails> getCurrentUser();
}
