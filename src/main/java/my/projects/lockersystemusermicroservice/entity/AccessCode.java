package my.projects.lockersystemusermicroservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Entity
@Table(name = "access_code")
public class AccessCode {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false, unique = true, name = "package_id")
    private String packageId;

    @Column(nullable = false, name = "expires_at")
    private LocalDateTime expiresAt;

    private boolean used;

    @ManyToOne
    private User user;

    public AccessCode() {
        this.expiresAt = LocalDateTime.now();
        this.code = RandomGenerator.getDefault().toString();
        this.packageId = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getPackageId() {
        return packageId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}