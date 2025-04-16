package my.projects.lockersystemusermicroservice.service.impl;

import my.projects.lockersystemusermicroservice.entity.User;
import my.projects.lockersystemusermicroservice.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return mapUserDetails(user);
    }

    private static UserDetails mapUserDetails(User user) {
        return new my.projects.lockersystemusermicroservice.entity.UserDetails(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                user.getId(),
                user.getFullName()
        );
    }
}
