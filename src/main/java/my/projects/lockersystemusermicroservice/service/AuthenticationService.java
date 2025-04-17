package my.projects.lockersystemusermicroservice.service;

import my.projects.lockersystemusermicroservice.dto.RegisterUserDTO;

public interface AuthenticationService {

    boolean register(RegisterUserDTO registerUserDTO);

}
