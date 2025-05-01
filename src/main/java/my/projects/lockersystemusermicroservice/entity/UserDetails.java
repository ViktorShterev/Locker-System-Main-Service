package my.projects.lockersystemusermicroservice.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class UserDetails extends User {

    private final UUID uuid;
    private final String fullName;

    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, UUID uuid, String fullName) {
        super(username, password, authorities);
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }
}
