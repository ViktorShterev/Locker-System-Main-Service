package my.projects.lockersystemusermicroservice.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetails extends User {

    private final Long userId;
    private final String fullName;

    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, String fullName) {
        super(username, password, authorities);
        this.userId = userId;
        this.fullName = fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }
}
