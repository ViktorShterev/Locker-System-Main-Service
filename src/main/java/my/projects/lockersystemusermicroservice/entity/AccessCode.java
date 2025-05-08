package my.projects.lockersystemusermicroservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_code")
public class AccessCode {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false, name = "package_id")
    private Long packageId;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    private boolean used;

    @ManyToOne
    private User user;

    public AccessCode() {
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Long getPackageId() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}